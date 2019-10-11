package server.repository

// Import the Slick interface for H2:
import slick.lifted.Tag
import slick.model.Table

final class UserTable(tag: Tag) extends Table[User](tag, "user") {

  def id = columns[Int]("id", O.PrimaryKey, O.AutoInc)
}
