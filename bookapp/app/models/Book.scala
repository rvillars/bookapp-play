package models

import scala.slick.session.Session
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import scala.slick.lifted.Query
import java.sql.Date

case class Book(id: Option[Long] = None,
  title: String,
  releaseDate: Date,
  author: Author)

object Books extends Table[Book]("Book") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def releaseDate = column[Date]("releaseDate")
  def authorId = column[Long]("authorId")
  def author = foreignKey("authorId", authorId, Authors)(_.id)

  def * = id.? ~ title ~ releaseDate ~ authorId <> (
      (id,title,releaseDate,authorId) => Book(id, title, releaseDate, Authors.read(authorId).get),
      (book:Book) => Some((Option(book.id.get), book.title, book.releaseDate, book.author.id.get))
  )
  
  def autoInc = id.? ~ title ~ releaseDate ~ authorId <> (
      (id,title,releaseDate,authorId) => Book(id, title, releaseDate, Authors.read(authorId).get),
      (book:Book) => Some((Option(book.id.get), book.title, book.releaseDate, book.author.id.get))
  ) returning id
  
  def list(): List[Book] = {
    val s = (for (s <- Books) yield s)
    s.list map { case a: Book => a }
  }

  def read(id: Long): Option[Book] = {
    val s = (for { s <- Books if (s.id === id) } yield (s)).list map { case a: Book => a }
    if (s.size > 0) Some(s.head)
    else None
  }

  def create(book: Book): Option[Book] = {
    val newId = Books.autoInc.insert(book)
    read(newId)
  }

  def update(book: Book) = {
    Books.where(_.id === book.id.get).update(book)
  }

  def delete(book: Book) = {
    Books.where(_.id === book.id.get).delete
  }
}