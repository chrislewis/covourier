name := "covourier"

organization := "chrslws"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.11.690",
  "org.scalatest" %% "scalatest" % "3.1.0" % "test"
)

scalafmtOnCompile := true

initialCommands := "import chrslws.covourier._; import chrslws.covourier.service._; val s = new DeliveryImpl()"
