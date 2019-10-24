package server.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import server.repository.User
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val userJsonFormat: RootJsonFormat[User] =  jsonFormat7(User)
}
