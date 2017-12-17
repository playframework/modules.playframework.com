name := """play-module-directory"""
organization := "com.playframework"
organizationName := "Lightbend"
startYear := Some(2017)
licenses += ("Apache-2.0", new URL(
  "https://www.apache.org/licenses/LICENSE-2.0.txt"))

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, SbtWeb, AutomateHeaderPlugin)

scalaVersion := "2.12.4"

resolvers += Resolver.sbtPluginRepo("releases")
resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  ehcache,
  ws,
  "com.h2database" % "h2" % "1.4.192",
  "net.codingwell" %% "scala-guice" % "4.1.0",
  "com.iheart" %% "ficus" % "1.4.3",
  evolutions,
  specs2 % Test,
  guice,
  filters
)

libraryDependencies ++= Seq(
  "com.mohiva" %% "play-silhouette" % "5.0.2",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "5.0.2",
  "com.mohiva" %% "play-silhouette-persistence" % "5.0.2",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "5.0.2",
  "com.mohiva" %% "play-silhouette-testkit" % "5.0.2" % "test"
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0",
  "mysql" % "mysql-connector-java" % "5.1.37"
)

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.6.1",
  "org.webjars" % "bootstrap" % "3.3.7-1" exclude("org.webjars", "jquery"),
  "org.webjars" % "jquery" % "3.2.1"
)

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

import de.heikoseeberger.sbtheader.FileType
import play.twirl.sbt.Import.TwirlKeys
headerMappings := headerMappings.value + (FileType("html") -> HeaderCommentStyle.twirlStyleBlockComment)
unmanagedSources.in(Compile, headerCreate) ++= sources
  .in(Compile, TwirlKeys.compileTemplates)
  .value

resolvers += Resolver.url("Typesafe Ivy releases", url("https://repo.typesafe.com/typesafe/ivy-releases"))(Resolver.ivyStylePatterns)
