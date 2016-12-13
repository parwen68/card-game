package se.callista.cardgame.demo

import scala.scalajs.js.JSApp

object GameJSApp extends JSApp {
  override def main(): Unit = println(new Game().run())
}
