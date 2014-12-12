import sbt._
import Keys._

object BuildSettings {

  object Basic {
    val settings = Seq(
      organization := "lab",
      version <<= version in ThisBuild,
      scalaVersion := "2.11.4",
      resolvers := Dependencies.resolvers,
      scalacOptions ++= Seq(
        "-encoding", "UTF-8",
        "-deprecation",
        "-feature",
        "-unchecked",
        "-Ywarn-dead-code",
        "-target:jvm-1.7"
      ),
      javacOptions ++= Seq(
        "-source", "1.7",
        "-target", "1.7",
        "-Xlint:unchecked",
        "-Xlint:deprecation",
        "-Xlint:-options"
      )
    )
  }

}