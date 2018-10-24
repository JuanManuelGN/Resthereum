package org.lambda.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._

object Resource extends App {

  implicit val actorSystem = ActorSystem("system")
  implicit val actorMaterializer = ActorMaterializer()

  lazy val route =
    AccountResource.route ~ BlockResource.route

  val port = 9010
  Http().bindAndHandle(route, "localhost", port)

  println(s"server started at $port")
}
