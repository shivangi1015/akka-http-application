package server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import server.controller.RouteHandler

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Boot extends App {

  implicit val system: ActorSystem = ActorSystem("helloworld")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val router = new RouteHandler
  val binding = Http()
    .bindAndHandle(router.route, ConfigReader.host, ConfigReader.port)
  binding.onComplete {
    case Success(_) => println("success")
    case Failure(error) => println(s"Error: ${error.getMessage}")
  }

}
