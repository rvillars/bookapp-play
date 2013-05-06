package models

import scala.slick.session.Session
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import scala.slick.lifted.Query

case class Author(id: Option[Long] = None,
  firstname: String,
  lastname: String)

object Authors extends Table[Author]("Author") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def firstname = column[String]("firstname")
  def lastname = column[String]("lastname")

  def * = id.? ~ firstname ~ lastname <> (Author, Author.unapply _)
  def autoInc = id.? ~ firstname ~ lastname <> (Author, Author.unapply _) returning id

  def list(): List[Author] = {
    val s = (for (s <- Authors) yield s)
    s.list map { case a: Author => a }
  }

  def read(id: Long): Option[Author] = {
    val s = (for { s <- Authors if (s.id === id) } yield (s)).list map { case a: Author => a }
    if (s.size > 0) Some(s.head)
    else None
  }

  def create(author: Author): Option[Author] = {
    val newId = Authors.autoInc.insert(author)
    read(newId)
  }

  def update(author: Author) = {
    Authors.where(_.id === author.id.get).update(author)
  }

  def delete(author: Author) = {
    Authors.where(_.id === author.id.get).delete
  }
}