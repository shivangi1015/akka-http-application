package server.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives.{entity, _}
import akka.http.scaladsl.server.Route
import server.repository.{BulkUser, User, UserRepository}
import server.controller.JsonSupport._

import scala.concurrent.ExecutionContextExecutor

class RouteHandler {

  implicit val system: ActorSystem = ActorSystem("users")
  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher
  val route: Route =
    path("user") {
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
      path("users") {
        post {
          entity(as[BulkUser]) { bulkUser =>
            complete {
              val inserted = UserRepository.bulkInsert(bulkUser.users)
              inserted.map {
                result => HttpResponse(entity = result.getOrElse(0) + " rows inserted")
              }
            }
          }
        }
      } ~
      pathPrefix("user" / IntNumber) { id =>
        get {
          complete {
            UserRepository.userById(id).map {
              result => HttpResponse(entity = "Users: " + result)
            }
          }
        }
      } ~
      pathPrefix("user-all") {
        get {
          complete {
            UserRepository.listAllUsers()
          }
        }
      } ~
      pathPrefix("delete" / IntNumber) { id =>
        delete {
          complete {
            UserRepository.deleteUserById(id).map {
              result => HttpResponse(entity = result + " deleted.")
            }
          }
        }
      } ~
      pathPrefix("update" / IntNumber) { id => {
        path(Segment) { name =>
          put {
            complete {
              UserRepository.updateName(id, name).map {
                result => HttpResponse(entity = result + " updated")
              }
            }
          }
        }
      }

      }
}



