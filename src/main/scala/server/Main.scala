package server

import server.repository.{User, UserRepository}
import scala.concurrent.ExecutionContext.Implicits.global
object Main extends App {

  val userRepository = UserRepository

  userRepository.store(User(1, "shivangi", "gupta", 26, "f", "gzb", "up"))

  userRepository.listAllUsers() foreach println
}
