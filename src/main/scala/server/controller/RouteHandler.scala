package server.controller

import akka.http.scaladsl.server.Directives.{complete, get, path, post}
import akka.http.scaladsl.server.Route

class RouteHandler {

  def route: Route = path("hello") {
    get {
      complete("Hello, World!")
    }
  }
}
