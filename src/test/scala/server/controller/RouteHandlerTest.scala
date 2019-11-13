package server.controller

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.mockito.Mockito._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import server.controller.JsonSupport._
import server.repository.{BulkUser, User, UserRepository}

import scala.concurrent.Future

class RouteHandlerTest extends WordSpec with Matchers with ScalaFutures
  with ScalatestRouteTest with MockitoSugar {

  val mockedUserRepo: UserRepository = mock[UserRepository]
  val routeHandler = new RouteHandler(mockedUserRepo)
  val routes: Route = routeHandler.route
  val user = User(2, "shivangi", "gupta", 26, "f", "up", "india")

  "run post route to add user" in {

    when(mockedUserRepo.store(user)).thenReturn(Future.successful(1))
    val userEntity = Marshal(user).to[MessageEntity].futureValue
    val request = Post("/user").withEntity(userEntity)
    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      entityAs[String] should ===("1 row inserted")
    }
  }

  "run post route to add bulk users" in {
    when(mockedUserRepo.bulkInsert(List(user))).thenReturn(Future.successful(Some(1)))
    val userEntity = Marshal(BulkUser(List(user))).to[MessageEntity].futureValue
    val request = Post("/users").withEntity(userEntity)
    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      entityAs[String] should ===("1 rows inserted")
    }
  }

  "run get route to get user by id" in {
    when(mockedUserRepo.userById(2)).thenReturn(Future.successful(List(user)))
    val request = Get("/user/2")

    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      entityAs[String] should ===("Users: List(User(2,shivangi,gupta,26,f,up,india))")
    }
  }

  "run get route to get all users" in {
    when(mockedUserRepo.listAllUsers()).thenReturn(Future.successful(List(user)))
    val request = Get("/user-all")

    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      entityAs[String] should ===("Users: List(User(2,shivangi,gupta,26,f,up,india))")
    }
  }

  "run update route" in {
    when(mockedUserRepo.updateName(2, "shiv")).thenReturn(Future.successful(1))
    val request = Put("/update/2/shiv")
    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      entityAs[String] should ===("1 row updated.")
    }
  }

  "run delete route" in {
    when(mockedUserRepo.deleteUserById(1)).thenReturn(Future.successful(1))
    val request = Delete("/delete/1")

    request ~> routes ~> check {
      status should ===(StatusCodes.OK)
      entityAs[String] should ===("1 row deleted.")
    }
  }

}
