package server

import server.repository.{User, UserRepository}
import scala.concurrent.ExecutionContext.Implicits.global
object Main extends App {

  val userRepository = UserRepository

  /*userRepository.store(User(1, "shivangi", "gupta", 26, "f", "gzb", "up"))

  Thread.sleep(5000)*/
  println("reading...")
  userRepository.listAllUsers().foreach{x =>
    println("in foreach")
    println(x)
  }
  Thread.sleep(5000)
}
