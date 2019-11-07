package server.repository

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FunSuite, Matchers, WordSpec}
import slick.jdbc.H2Profile.api._

class UserRepositoryTest extends WordSpec with Matchers with ScalaFutures
  with ScalatestRouteTest {

  val userRepo: UserRepository.type = UserRepository
  val user = User(1, "shivangi", "gupta", 26, "f", "up", "india")

  "User Repository Test" should {
    "test store in table" in {
      userRepo.store(user) map {
        rowsInserted =>
          println(s"::: $rowsInserted")
          rowsInserted should ===(5)
      }
    }

    /*"test bulk insert" in {
      val users = List(
        User(2, "A", "Gupta", 30, "m", "UP", "India"),
        User(3, "S", "Gupta", 30, "f", "UP", "India")
      )

      userRepo.bulkInsert(users) map {
        rowsInserted => assert(rowsInserted == Option(2))
      }
    }

    "test update" in {
      userRepo.updateName(1, "Shivangi") map {
        rowsUpdated => assert(rowsUpdated == 1)
      }
    }

    "test get all users" in {
      userRepo.listAllUsers().map {
        rows => assert(rows.lengthCompare(3) == 0)
      }
    }

    "test get user by id" in {
      userRepo.userById(5) map {
        response => println(response)
          println(response.head.id)
          assert(response.head.id.equals(1))
      }
    }*/

  }
}
