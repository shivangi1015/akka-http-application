package server.controller

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import server.repository.User
import spray.json.RootJsonFormat
import spray.json.DefaultJsonProtocol


trait JsonSupport extends SprayJsonSupport{

  import DefaultJsonProtocol._
  implicit val userJsonFormat: RootJsonFormat[User] =  jsonFormat7(User)
}
