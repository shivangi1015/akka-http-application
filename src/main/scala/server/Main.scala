package server

import server.repository.{User, UserRepository}
import scala.concurrent.ExecutionContext.Implicits.global
object Main extends App {

  val userRepository = UserRepository

  val users = List(
    User(2, "Ashok", "Gupta", 62, "m", "UP", "India"),
    User(3, "Santosh", "Gupta", 61, "f", "UP", "India")
  )
  val response =
  for {
    rows <- userRepository.listAllUsers()
  } yield rows

  Thread.sleep(5000)
  response.foreach { res =>
    println(s"Rows $res")
    println(s"Rows ${res.size}")
  }
  Thread.sleep(5000)
}
