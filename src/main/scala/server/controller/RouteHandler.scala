package server.controller

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives.{entity, _}
import akka.http.scaladsl.server.Route
import server.repository.{User, UserRepository}

import scala.concurrent.ExecutionContextExecutor

class RouteHandler extends JsonSupport {

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
          entity(as[List[User]]) { users =>
            complete {
              val inserted = UserRepository.bulkInsert(users)
              inserted.map {
                result => HttpResponse(entity = result + " rows inserted")
              }
            }
          }
        }
      } ~
      pathPrefix("user" / IntNumber) { id =>
        get {
          complete {
            UserRepository.userById(id)
          }
        }
      } ~
      pathPrefix("user-all") {
        get {
          complete {
            UserRepository.listAllUsers()
          }
        }
      }
  /* }
 }
   path("insert") {
     post {
       entity(as[Int]) { user =>
         complete {
           /*val inserted = UserRepository.store(user)
           inserted.map {
             result => HttpResponse(entity = result + " row inserted")
           }*/
           HttpResponse(entity = " row inserted")
         }
       }
     }
   }/* ~
     path("delete") {
       get {
         entity(as[Int]) {
           id =>
             complete {
               UserRepository.deleteUserById(id).map {
                 result => HttpResponse(entity = result + " user deleted")
               }
             }
         }
       }
     }*/*/
}



