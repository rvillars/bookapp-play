package controllers

import scala.slick.driver.H2Driver.simple.Database
import scala.slick.driver.H2Driver.simple.Database.threadLocalSession
import scala.slick.driver.H2Driver.simple.columnExtensionMethods
import scala.slick.driver.H2Driver.simple.queryToQueryInvoker
import scala.slick.driver.H2Driver.simple.tableToQuery
import scala.slick.driver.H2Driver.simple.valueToConstColumn
import models.Book
import models.Books
import play.api.Play.current
import play.api.db.DB
import play.api.libs.functional.syntax.functionalCanBuildApplicative
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.Json
import play.api.libs.json.Writes.LongWrites
import play.api.libs.json.Writes.traversableWrites
import play.api.mvc.Action
import play.api.mvc.Controller
import models.Author

object BookController extends Controller {

  implicit val authorWrites = Json.writes[Author]
  implicit val authorReads = Json.reads[Author]
  implicit val bookWrites = Json.writes[Book]
  implicit val bookReads = Json.reads[Book]
  
  lazy val database = Database.forDataSource(DB.getDataSource())
  
  def list = Action {
    val json = database.withSession {
      Json.toJson(Books.list)
    }
    Ok(json).as(JSON)
  }
  
   def read(id: Long) = Action {
    val json = database.withSession {
      val book = Books.read(id).get
      Json.toJson(book)
    }
    Ok(json).as(JSON)
  }
   
  def create = Action(parse.json) { request =>
  	val book = request.body.validate[Book].get
  	val json = database.withSession {
  	  val createdBook = Books.create(book)
  	  Json.toJson(createdBook)
  	}
  	Ok(json)
  }
  
  def update(id: Long) = Action(parse.json) { request =>
  	val book = request.body.validate[Book].get
  	val json = database.withSession {
  		Books.update(book);
  		Json.toJson(book)
  	}
  	Ok(json)
  }

  def delete(id: Long) = Action {
    val json = database.withSession {
      val book = Books.read(id).get;
      Books.delete(book);
    }
    Ok
  }

}