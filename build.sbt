name := "card-game"

version := "1.0"

scalaVersion in ThisBuild := "2.12.1"

lazy val root = project.in(file(".")).
  aggregate(cardGameJS, cardGameJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val cardGame = crossProject.in(file(".")).
  settings(
    name := "card-game",
    version := "0.1-SNAPSHOT"
  ).
  jvmSettings(

  ).
  jsSettings(

  )

lazy val cardGameJVM = cardGame.jvm
lazy val cardGameJS = cardGame.js