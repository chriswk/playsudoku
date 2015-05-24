name := """sudoku"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

herokuAppName in Compile := "playsudoku"

resolvers += "rubygems-release" at "http://rubygems-proxy.torquebox.org/releases"


libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "org.webjars" % "bootstrap" % "3.3.4"
)

libraryDependencies ++= Seq(
  "rubygems" % "travis" % "1.7.1" excludeAll(ExclusionRule("rubygems", "pry", "*"), ExclusionRule("rubygems", "ffi", "*")),
  "rubygems" % "pry" % "0.9.12.6",
  "rubygems" % "ffi" % "1.9.3"
)

