package org.lambda.rest

import akka.http.scaladsl.server.Directives.{complete, get, path, pathPrefix}
import org.lambda.service.BlockService
import akka.http.scaladsl.server.Directives._

object BlockResource extends App {
  lazy val route =
    pathPrefix("api") {
      /**
        * Get numbers of block mined blocks
        */
      path("blockNumbers") {
        get {
          complete {
            BlockService.getblockNumber
          }
        }
      }~
        path("mining") {
          get {
            complete {
              BlockService.mining
            }
          }
        }
    }
}
