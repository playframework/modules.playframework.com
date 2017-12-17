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

package utils.auth

import com.mohiva.play.silhouette.api.{ Authenticator, Authorization }
import models.User
import play.api.mvc.Request

import scala.concurrent.Future

/**
 * Grants only access if a user has authenticated with the given provider.
 *
 * @param provider The provider ID the user must authenticated with.
 * @tparam A The type of the authenticator.
 */
case class WithProvider[A <: Authenticator](provider: String) extends Authorization[User, A] {

  /**
   * Indicates if a user is authorized to access an action.
   *
   * @param user The usr object.
   * @param authenticator The authenticator instance.
   * @param request The current request.
   * @tparam B The type of the request body.
   * @return True if the user is authorized, false otherwise.
   */
  override def isAuthorized[B](user: User, authenticator: A)(
    implicit
    request: Request[B]): Future[Boolean] = {

    Future.successful(user.loginInfo.providerID == provider)
  }
}