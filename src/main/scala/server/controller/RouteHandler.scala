package server.controller

import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route

object RouteHandler {

  def route: Route = path("hello") {
    get {
      complete("Hello, World!")
    }
  }
}