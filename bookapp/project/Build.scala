import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "bookapp"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
	"com.typesafe.slick" % "slick_2.10" % "1.0.0",
    "com.h2database" % "h2" % "1.2.127",
    jdbc
  )
  
  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
