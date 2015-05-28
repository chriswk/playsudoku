name := """sudoku"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

herokuAppName in Compile := "playsudoku"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "org.webjars" % "bootstrap" % "3.3.4",
  "org.webjars" % "jquery" % "2.1.4",
  "com.sksamuel.elastic4s" %% "elastic4s" % "1.5.10",
  "org.scalacheck" %% "scalacheck" % "1.12.3" % "test"
)


fork in run := true