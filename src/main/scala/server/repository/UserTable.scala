package server.repository

// Import the Slick interface for H2:
import slick.jdbc.H2Profile.api._

trait UserTable {

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

}
