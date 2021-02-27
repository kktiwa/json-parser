

version := "0.1"

scalaVersion := "2.11.12"

lazy val commonSettings = Seq(
  libraryDependencies := allDependencies
)

lazy val core = (project in file("."))
  .settings(
    name := "json-parser",
    commonSettings
  ).settings(skip in publish := true)

lazy val dependencies = new {
  val circeVersion = "0.12.0-M3"
  val circeConfigVersion = "0.7.0-M1"
  val scalaTestVersion = "2.2.6"
  val enumeratumVersion = "1.5.13"

  //core dependencies
  val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-generic-extras").map(_ % circeVersion)
  val circeConfig = "io.circe" %% "circe-config" % circeConfigVersion
  val enumeratum = "com.beachape" %% "enumeratum" % enumeratumVersion
  val akka = Seq("com.typesafe.akka" %% "akka-actor" % "2.5.13",
    "com.typesafe.akka" %% "akka-stream" % "2.5.13",
    "com.typesafe.akka" %% "akka-http" % "10.1.3"
  )

  // test dependencies
  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion
}

lazy val allDependencies = dependencies.circe ++ Seq(
  dependencies.scalaTest,
  dependencies.circeConfig,
  dependencies.enumeratum
) ++ dependencies.akka