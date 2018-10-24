package org.lambda.rest

import akka.http.scaladsl.server.Directives._
import org.json4s.DefaultFormats
import org.lambda.converter.ToJson
import org.lambda.model.Transaction
import org.lambda.service.AccountService

object AccountResource extends App {

  implicit val formats: DefaultFormats.type = DefaultFormats

  lazy val route =
    pathPrefix("api") {
      path("accounts") {
        get {
          complete {
            ToJson.toJson(AccountService.getAccounts)
          }
        }
      } ~
        path("balance" / Segment) { account =>
          get {
            complete {
              AccountService.getBalance(account)
            }
          }
        } ~
        path("sendTransaction" / Segment / Segment / Segment) {
          (from, to, amount) =>
            get {
              complete {
                AccountService.sendTransaction(Transaction(from, to, amount.toLong))
              }
            }
        } ~
        path("transactionCount" / Segment) { account =>
          get {
            complete {
              AccountService.getTransactionCount(account)
            }
          }
        }
    }
}
