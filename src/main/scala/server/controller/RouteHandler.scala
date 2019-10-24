package server.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import server.repository.{User, UserRepository}

import scala.concurrent.ExecutionContextExecutor

class RouteHandler {

  import server.controller.JsonSupport._
  implicit val system: ActorSystem = ActorSystem("users")
  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher
  val route: Route =
    path("insert") {
      post {
        entity(as[User]) { user =>
          complete {
            val inserted = UserRepository.store(user)
            inserted.map {
              result => HttpResponse(entity = result + " row inserted")
            }
          }
        }
      }
    } ~
      path("delete") {
        post {
          entity(as[Int]) {
            id =>
              complete {
                UserRepository.deleteUserById(id).map {
                  result => HttpResponse(entity = result + " user deleted")
                }
              }
          }
        }
      }
}



