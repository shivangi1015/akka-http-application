package server.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import server.repository.{User, BulkUser}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val userJsonFormat: RootJsonFormat[User] =  jsonFormat7(User)
  implicit val listUserJsonFormat: RootJsonFormat[BulkUser] = jsonFormat1(BulkUser)
}
