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

import javax.inject.Inject
import play.api.cache._
import play.api.mvc.Action
import play.api.mvc.Controller
import helpers.Auth0Config
import java.security.SecureRandom
import java.math.BigInteger

class Application @Inject() (cache: CacheApi) extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def login = Action {
    val config = Auth0Config.get()
    // Generate random state parameter
    object RandomUtil {
      private val random = new SecureRandom()

      def alphanumeric(nrChars: Int = 24): String = {
        new BigInteger(nrChars * 5, random).toString(32)
      }
    }
    val state = RandomUtil.alphanumeric()
    cache.set("state", state)

    val query = String.format(
      "authorize?prompt=consent&response_type=code&redirect_uri=%s&scope=public_repo&state=%s&client_id=%s",
      config.callbackURL,
      state,
      config.clientId)

    Redirect(String.format("https://%s/%s", config.domain, query))
  }

  def logout = Action {
    val config = Auth0Config.get()
    Redirect(routes.Application.index()).withNewSession
  }
}
