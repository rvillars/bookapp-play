package controllers

import scala.slick.driver.H2Driver.simple.Database
import scala.slick.driver.H2Driver.simple.Database.threadLocalSession
import scala.slick.driver.H2Driver.simple.columnExtensionMethods
import scala.slick.driver.H2Driver.simple.queryToQueryInvoker
import scala.slick.driver.H2Driver.simple.tableToQuery
import scala.slick.driver.H2Driver.simple.valueToConstColumn

import models.Author
import models.Authors
import play.api.Play.current
import play.api.db.DB
import play.api.libs.functional.syntax.functionalCanBuildApplicative
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.Json
import play.api.libs.json.Writes.LongWrites
import play.api.libs.json.Writes.traversableWrites
import play.api.mvc.Action
import play.api.mvc.Controller

object AuthorController extends Controller {

  implicit val authorWrites = Json.writes[Author]
  implicit val authorReads = Json.reads[Author]
  
  lazy val database = Database.forDataSource(DB.getDataSource())
  
  def list = Action {
    val json = database.withSession {
      Json.toJson(Authors.list)
    }
    Ok(json).as(JSON)
  }
  
   def read(id: Long) = Action {
    val json = database.withSession {
      val author = Authors.read(id).get
      Json.toJson(author)
    }
    Ok(json).as(JSON)
  }
   
  def create = Action(parse.json) { request =>
  	val author = request.body.validate[Author].get
  	val json = database.withSession {
  	  val createdAuthor = Authors.create(author)
  	  Json.toJson(createdAuthor)
  	}
  	Ok(json)
  }
  
  def update(id: Long) = Action(parse.json) { request =>
  	val author = request.body.validate[Author].get
  	val json = database.withSession {
  		Authors.update(author);
  		Json.toJson(author)
  	}
  	Ok(json)
  }

  def delete(id: Long) = Action {
    val json = database.withSession {
      val author = Authors.read(id).get;
      Authors.delete(author);
    }
    Ok
  }

}