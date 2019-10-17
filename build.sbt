name := "AkkaHttpApplication"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-actor" % "2.5.13",
  "com.typesafe.akka" %% "akka-stream" % "2.5.13",
  "com.typesafe.akka" %% "akka-http" % "10.1.3",
  "org.scalatest" %% "scalatest" % "3.0.8-RC5" % Test,
  "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided",
  "com.typesafe.slick" %% "slick" % "3.3.0",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.3" % Test,
  "com.typesafe.slick" %% "slick" % "3.3.0",
  "com.h2database" % "h2" % "1.4.197",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.0",
  "org.postgresql" % "postgresql" % "9.4.1212"
)