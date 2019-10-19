package server.repository

import db.{DbComponent, PostgresDbComponent}

import scala.concurrent.Future

trait UserRepository extends WriteRepository with ReadRepository

object UserRepository extends UserRepository with WriteRepositoryImpl with ReadRepositoryImpl with
  DbComponent with PostgresDbComponent

trait WriteRepository {

  def store(user: User): Future[Int]

  def updateName(id: Int, name: String): Future[Int]

  def bulkInsert(users: List[User]): Future[Option[Int]]

  def deleteUserById(id: Int): Future[Int]
}

trait WriteRepositoryImpl extends WriteRepository {

  this: DbComponent with UserTable =>

  import driver.api._

  override def store(user: User): Future[Int] = {
    db.run(userTableQuery += user)
  }

  override def bulkInsert(users: List[User]): Future[Option[Int]] = {
    db.run(userTableQuery ++= users)
  }

  override def updateName(id: Int, name: String): Future[Int] = {
    db.run(userTableQuery.filter(_.id === id).map(user => user.fname).update(name))
  }

  override def deleteUserById(id: Int): Future[Int] = {
    db.run(userTableQuery.filter(_.id === id).delete)
  }
}

trait ReadRepository {

  def listAllUsers(): Future[Seq[User]]

  def userById(id: Int): Future[Seq[User]]
}

trait ReadRepositoryImpl extends ReadRepository with UserTable {

  this: DbComponent with UserTable =>

  import driver.api._

  override def listAllUsers(): Future[Seq[User]] = {
    println("in method...")
    db.run(userTableQuery.result)
  }

  override def userById(id: Int): Future[Seq[User]] = {
    db.run(userTableQuery.filter(_.id === id).result)
  }
}

trait UserTable {

  this: DbComponent =>

  import driver.api._

  class UserTableMapping(tag: Tag) extends Table[User](tag, "users") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def fname = column[String]("fname")

    def lname = column[String]("lname")

    def age = column[Int]("age")

    def gender = column[String]("gender")

    def state = column[String]("state")

    def country = column[String]("country")

    def * = (id, fname, lname, age, gender, state, country).mapTo[User]
  }

  lazy val userTableQuery = TableQuery[UserTableMapping]
}


