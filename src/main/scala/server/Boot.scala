package server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import server.controller.RouteHandler
import com.softwaremill.macwire._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Boot extends App {

  implicit val system: ActorSystem = ActorSystem("helloworld")
  implicit val executor: ExecutionContext = system.dispatcher
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  lazy val router = wire[RouteHandler]
  val binding = Http()
    .bindAndHandle(router.route, ConfigReader.HOST, ConfigReader.PORT)
  binding.onComplete {
    case Success(_) => println("success")
    case Failure(error) => println(s"Error: ${error.getMessage}")
  }

}
