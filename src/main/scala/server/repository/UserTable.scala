package server.repository

// Import the Slick interface for H2:
import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.duration._

object UserTable extends App {

  // Create an in-memory H2 database;
  val db = Database.forConfig("chapter01")

  class UserTableMapping(tag: Tag) extends Table[User](tag, "user") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def fname = column[String]("fname")

    def lname = column[String]("lname")

    def age = column[Int]("age")

    def gender = column[String]("gender")

    def state = column[String]("state")

    def country = column[String]("country")

    def * = (id, fname, lname, age, gender, state, country).mapTo[User]
  }

  lazy val messages = TableQuery[UserTableMapping]

  //  val action: DBIO[Unit] = messages.schema.create

  // Helper method for running a query in this example file:
  def exec[T](program: DBIO[T]): T = Await.result(db.run(program), 2 seconds)

  println("creating table")
  exec(messages.schema.create)

  def insertIntTable(user: User): Int = {
    println("inserting in table")
    //    exec(messages += User(1, "shivangi", "gupta", 26, "f", "UP", "India"))
    exec(messages += user)
  }

  def listAllUsers(): Seq[User] = {
    println("Selecting messages")
    exec(messages.result)
  }

  def userById(id: Int): Seq[User] = {
    exec(messages.filter(_.id === id).result)
  }

  def bulkInsert(users: List[User]): Option[Int] = {
    exec(messages ++= users)
  }

  def deleteUserById(id: String): Int = {
    exec(messages.filter(user => user.id === id).delete)
  }

  def updateName(id: Int, name: String) = {
    exec(messages.filter(_.id === id).map(user => user.fname).update(name))
  }
}

