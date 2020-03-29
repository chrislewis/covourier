name := "covourier"

organization := "chrslws"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.11.690",
  "io.circe" %% "circe-core" % "0.13.0",
  "io.circe" %% "circe-generic" % "0.13.0",
  "io.circe" %% "circe-parser" % "0.13.0",
  "io.netty" % "netty-buffer" % "4.1.48.Final",
  "io.netty" % "netty-codec-http" % "4.1.48.Final",
  "io.netty" % "netty-handler" % "4.1.48.Final",
  "org.scalatest" %% "scalatest" % "3.1.0" % "test"
)

scalafmtOnCompile := true

initialCommands := "import chrslws.covourier._; import chrslws.covourier.model._; import chrslws.covourier.service._; val s = DynamoDbDeliveryService.local"
