name := "covourier"

organization := "chrslws"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % "test"

scalafmtOnCompile := true

initialCommands := "import chrslws.covourier._"
