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

package helpers

import play.api.Play

case class Auth0Config(secret: String, clientId: String, callbackURL: String, domain: String)
object Auth0Config {
  def get() = {
    Auth0Config(
      Play.current.configuration.getString("auth0.clientSecret").get,
      Play.current.configuration.getString("auth0.clientId").get,
      Play.current.configuration.getString("auth0.callbackURL").get,
      Play.current.configuration.getString("auth0.domain").get)
  }
}
