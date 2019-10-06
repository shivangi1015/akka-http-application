package server.routes

import akka.http.scaladsl.server.Directives.{complete, get, path}
import akka.http.scaladsl.server.Route

object route {

  def route: Route = path("hello") {
    get {
      complete("Hello, World!")
    }
  }
}
