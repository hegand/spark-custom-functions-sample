lazy val buildSettings = Seq(
  name := "spark-custom-functions",
  version := "0.1",
  organization := "com.ge.fdl",
  scalaVersion := "2.11.12"
)

val app = (project in file(".")).
  settings(buildSettings: _*)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "2.3.1.3.0.2.0-50" % "provided",
  "org.apache.spark" %% "spark-core" % "2.3.1.3.0.2.0-50"  %"provided"
)

resolvers := List("Hortonworks Releases" at "http://repo.hortonworks.com/content/repositories/releases/")