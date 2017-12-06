/*
 * Copyright 2017 Lightbend
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import javax.inject.Inject
import play.api.cache._
import play.api.http.HeaderNames
import play.api.http.MimeTypes
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Json.toJsFieldJsValueWrapper
import play.api.libs.ws._
import play.api.mvc.{ Action, Controller }
import helpers.Auth0Config

class Callback @Inject() (cache: CacheApi, ws: WSClient) extends Controller {

  def callback(codeOpt: Option[String] = None, stateOpt: Option[String] = None) = Action.async {
    if (stateOpt == cache.get("state")) {
      (for {
        code <- codeOpt
      } yield {
        getToken(code).flatMap { accessToken =>
          getUser(accessToken).map { user =>
            cache.set("profile", user)
            Redirect(routes.User.index())
              .withSession(
                "accessToken" -> accessToken)
          }
        }.recover {
          case ex: IllegalStateException => Unauthorized(ex.getMessage)
        }
      }).getOrElse(Future.successful(BadRequest("No parameters supplied")))
    } else {
      Future.successful(BadRequest("Invalid state parameter"))
    }
  }

  def getToken(code: String): Future[String] = {
    val config = Auth0Config.get()

    val tokenResponse = ws.url(String.format("https://%s/access_token", config.domain)).
      withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON).
      withQueryString(
        "client_id" -> config.clientId,
        "client_secret" -> config.secret,
        "code" -> code).
        post(EmptyBody)

    tokenResponse.flatMap { response =>
      (response.json \ "access_token").asOpt[String].fold(Future.failed[String](new IllegalStateException("Error"))) { accessToken =>
        Future.successful(accessToken)
      }
    }

  }

  def getUser(accessToken: String): Future[JsValue] = {
    val config = Auth0Config.get()
    val userResponse = ws.url("https://api.github.com/user/repos").
      withHeaders(HeaderNames.AUTHORIZATION -> s"token $accessToken").
      get()

    userResponse.flatMap(response => Future.successful(response.json))
  }
}
