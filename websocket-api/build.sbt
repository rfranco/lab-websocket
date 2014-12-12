import sbt._
import Keys._
import Dependencies._
import BuildSettings._

Basic.settings

enablePlugins(play.PlayScala)

name := "websocket-api"

libraryDependencies ++= Seq(
  akka.actor
)