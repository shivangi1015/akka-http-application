package server.controller

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}


class RouteHandlerTest extends WordSpec with Matchers with ScalaFutures
  with ScalatestRouteTest {

  val routeHandler = new RouteHandler
  val routes: Route = routeHandler.route

  "Route Teser" should {
    "run get route" in {
      val request = HttpRequest(uri = "/hello")
      request ~> routes ~> check {

        status === StatusCodes.OK
      }
    }
  }
}
