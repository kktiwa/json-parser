
name := "json-parser"
ThisBuild / organization := "au.com.cba"
ThisBuild / scalaVersion := "2.11.12"

lazy val commonSettings = Seq(
  libraryDependencies := allDependencies
)

lazy val assemblySettings = Seq(
  assemblyMergeStrategy in assembly := {
    case PathList("mozilla", _@_*) => MergeStrategy.discard
    case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.discard
    case x => {
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
    }
  }
)

lazy val core = (project in file("."))
  .settings(
    commonSettings
  ).settings(
  commonSettings,
  assemblySettings
)

lazy val dependencies = new {
  val circeVersion = "0.12.0-M3"
  val circeConfigVersion = "0.7.0-M1"
  val scalaTestVersion = "2.2.6"
  val enumeratumVersion = "1.5.13"
  val akkaVersion = "2.5.13"
  val akkaHttpVersion = "10.1.3"
  val logBackVersion = "1.2.3"
  val typesafeLogVersion = "3.1.0"

  //core dependencies
  val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-generic-extras").map(_ % circeVersion)
  val circeConfig = "io.circe" %% "circe-config" % circeConfigVersion
  val enumeratum = "com.beachape" %% "enumeratum" % enumeratumVersion
  val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  )
  val logBack = "ch.qos.logback" % "logback-classic" % logBackVersion
  val typesafeLog = "com.typesafe.scala-logging" %% "scala-logging" % typesafeLogVersion

  // test dependencies
  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion
}

lazy val allDependencies = dependencies.circe ++ Seq(
  dependencies.scalaTest,
  dependencies.circeConfig,
  dependencies.enumeratum,
  dependencies.logBack,
  dependencies.typesafeLog
) ++ dependencies.akka


packageName in Docker := "json-parser-api"
dockerUpdateLatest := true
dockerExposedPorts in Docker := Seq(9000)
dockerRepository := Some("cba")
dockerBaseImage := "java"
enablePlugins(JavaAppPackaging, DockerPlugin)