package server.controller

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._
import server.repository.{User, UserRepository}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

import scala.concurrent.Future


class RouteHandlerTest extends WordSpec with Matchers with ScalaFutures
  with ScalatestRouteTest with MockitoSugar {

  val routeHandler = new RouteHandler
  val routes: Route = routeHandler.route
  val mockedUserRepo: UserRepository = mock[UserRepository]

  "run get route" in {
    val user = User(1, "shivangi", "gupta", 2, "f", "up", "india")
    when(mockedUserRepo.store(user)).thenReturn(Future.successful(1))
    val userEntity = Marshal(user).to[MessageEntity].futureValue
    val request = Post("/user").withEntity(userEntity)
    request ~> routes ~> check {
      status === StatusCodes.OK
    }
  }

}
