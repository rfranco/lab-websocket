import sbt._

object Dependencies {

  val resolvers = Seq(
    Resolver.defaultLocal,
    Resolver.mavenLocal,
    Resolver.sonatypeRepo("releases"),
    Resolver.typesafeRepo("releases"),
    Resolver.typesafeRepo("snapshots"),
    Resolver.sonatypeRepo("snapshots")
  )

  object akka {
    val version = "2.3.6"
    // Core Akka
    val actor = "com.typesafe.akka" %% "akka-actor" % version
    val cluster = "com.typesafe.akka" %% "akka-cluster" % version
    val contrib = "com.typesafe.akka" %% "akka-contrib" % version intransitive()
    val persistence = "com.typesafe.akka" %% "akka-persistence-experimental" % version intransitive()
    val persistence_cassandra = "com.github.krasserm" %% "akka-persistence-cassandra" % "0.3.4" intransitive()

    val leveldb = "org.iq80.leveldb" % "leveldb" % "0.7"

    val testkit = "com.typesafe.akka" %% "akka-testkit" % version
  }

  // Apple push notifications
  val apns = "com.notnoop.apns" % "apns" % "0.1.6"
  val slf4j_simple = "org.slf4j" % "slf4j-simple" % "1.6.1"

  // Testing
  val scalatest = "org.scalatest" %% "scalatest" % "2.2.1"
  val scalacheck = "org.scalacheck" %% "scalacheck" % "1.11.6"

}
