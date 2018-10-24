name := "web3scala-ethereum-integration"

version := "0.1"

scalaVersion := "2.12.6"

mainClass in (Compile, run) := Some("org.lambda.rest.Resource")

description := "Api Rest for integration with Ethereum clients through web3scala library"

libraryDependencies ++= Seq(
  "org.web3scala" % "core" % "0.1.0",
  "com.typesafe.akka" %% "akka-http" % "10.1.5",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.5" % Test,
  "com.typesafe.akka" %% "akka-actor" % "2.5.16",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.16" % Test,
  "com.typesafe.akka" %% "akka-stream" % "2.5.16",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.16" % Test,
  "net.liftweb" %% "lift-json" % "3.3.0-M2"
)