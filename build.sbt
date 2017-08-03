name := "WikimediaEventStreams"

version := "1.0"

scalaVersion := "2.11.8"

scalafmtOnCompile in ThisBuild := true

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-json" % "2.3.9",
  "com.typesafe.akka" %% "akka-http" % "10.0.9",
  "com.typesafe.akka" %% "akka-stream" % "2.5.3",
  "com.typesafe.akka" %% "akka-slf4j" % "2.5.3",
  "ch.qos.logback" % "logback-classic" % "1.1.7"
)
