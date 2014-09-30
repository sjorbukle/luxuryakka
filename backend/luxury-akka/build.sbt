name := """luxury-akka"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "com.nimbusds" % "nimbus-jose-jwt" % "2.25"
)
