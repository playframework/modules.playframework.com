name := """play-module-directory"""
organization := "com.playframework"
organizationName := "Lightbend"
startYear := Some(2017)
licenses += ("Apache-2.0", new URL(
  "https://www.apache.org/licenses/LICENSE-2.0.txt"))

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, AutomateHeaderPlugin)

scalaVersion := "2.12.4"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

import de.heikoseeberger.sbtheader.FileType
import play.twirl.sbt.Import.TwirlKeys
headerMappings := headerMappings.value + (FileType("html") -> HeaderCommentStyle.twirlStyleBlockComment)
unmanagedSources.in(Compile, headerCreate) ++= sources
  .in(Compile, TwirlKeys.compileTemplates)
  .value

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.playframework.modules.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.playframework.modules.binders._"
