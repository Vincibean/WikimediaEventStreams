name := "WikimediaEventStreams"

version := "1.0"

scalaVersion := "2.11.8"

scalafmtOnCompile in ThisBuild := true

val sparkVersion = "2.1.0"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.3.9",
  "com.typesafe.akka" %% "akka-http" % "10.0.9",
  "com.typesafe.akka" %% "akka-stream" % "2.5.3",
  "com.typesafe.akka" %% "akka-slf4j" % "2.5.3",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion
)
