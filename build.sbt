name := "content"
 
version := "0.0.0"
 
scalaVersion := "2.10.4"
 
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

libraryDependencies ++= Seq(
  	"ch.qos.logback" % "logback-classic" % "1.0.12",
  	"org.scalatest" %% "scalatest" % "2.2.1" % "test",
 	"org.scalatestplus" %% "play" % "1.2.0" % "test",
	"mysql" % "mysql-connector-java" % "5.1.18",
	anorm
  )

lazy val root = (project in file("."))
	.enablePlugins(PlayScala,SbtWeb)

libraryDependencies += javaJdbc

includeFilter in (Assets, LessKeys.less) := "*.less"

excludeFilter in (Assets, LessKeys.less) := "_*.less"

LessKeys.compress := true
