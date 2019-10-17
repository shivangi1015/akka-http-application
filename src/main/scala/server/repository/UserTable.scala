package server.repository

// Import the Slick interface for H2:
import slick.jdbc.H2Profile.api._

import scala.concurrent.Future

trait WriteRepository {

  def store(user: User): Future[Int]

  def updateName(id: Int, name: String): Future[Int]

  def bulkInsert(users: List[User]): Future[Option[Int]]

  def deleteUserById(id: Int): Future[Int]
}

trait WriteRepositoryImpl extends WriteRepository with UserTable {

  override def store(user: User): Future[Int] = {
    db.run(messages += user)
  }

  override def bulkInsert(users: List[User]): Future[Option[Int]] = {
    db.run(messages ++= users)
  }

  override def updateName(id: Int, name: String): Future[Int] = {
    db.run(messages.filter(_.id === id).map(user => user.fname).update(name))
  }

  override def deleteUserById(id: Int): Future[Int] = {
    db.run(messages.filter(_.id === id).delete)
  }
}

trait ReadRepository {

  def listAllUsers(): Future[Seq[User]]

  def userById(id: Int): Future[Seq[User]]
}

trait ReadRepositoryImpl extends ReadRepository with UserTable {

  override def listAllUsers(): Future[Seq[User]] = {
    db.run(messages.result)
  }

  override def userById(id: Int): Future[Seq[User]] = {
    db.run(messages.filter(_.id === id).result)
  }
}

trait UserTable extends App {

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


