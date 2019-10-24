package server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import server.controller.RouteHandler
import com.softwaremill.macwire._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Boot extends App {

  lazy val router = wire[RouteHandler]
  implicit val system: ActorSystem = router.system
  implicit val executor: ExecutionContext = router.dispatcher

  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val binding = Http()
    .bindAndHandle(router.route, ConfigReader.HOST, ConfigReader.PORT)
  binding.onComplete {
    case Success(_) => println("success")
    case Failure(error) => println(s"Error: ${error.getMessage}")
  }

}
