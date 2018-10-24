package org.lambda.service

import org.json4s
import org.json4s.DefaultFormats
import org.lambda.converter.{NumberFormatter, ToJson}
import org.lambda.model._
import org.web3scala.Service
import org.web3scala.model.{BlockName, EthSendTransactionObject}
import org.web3scala.util.Utils

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

trait EthereumService {
  implicit val formats: DefaultFormats.type = DefaultFormats
}

object BlockService extends EthereumService {
  val service: Service = new Service

  def getblockNumber: String = {
    val response = service.ethBlockNumber
    val blockNumber =
      response match {
        case Left(error) => IntegrationBlockNumber(0)
        case Right(source) => IntegrationBlockNumber(source.result)
      }
    ToJson.toJson(blockNumber)
  }

  def mining = {
    val response = service.asyncEthMining
    for (value <-
           service
             .asyncEthMining
             .future
             .map(json => (json \ "result").extract[Boolean]))
      yield ToJson.toJson(IntegrationMinig(value))
  }
}

object AccountService extends EthereumService {
  val service: Service = new Service

  def getAccounts: IntegrationAccounts = {
    val ethAccounts = service.asyncEthAccounts
    val fut =
      for (r <- ethAccounts.future)
        yield {
          r.children
        }
    Await.result(fut, Duration(100, "seconds"))
    val response =
      IntegrationAccounts(
        fut
          .value
          .get
          .get(2)
          .children
          .map(x => x.asInstanceOf[json4s.JString].s))
    response
  }

  def getBalance(account: String) = {
    for (value <-
           service
             .asyncEthGetBalance(account, BlockName("latest"))
             .future
             .map(json => (json \ "result").extract[String]))
      yield ToJson.toJson(IntegrationBalance(List((account, NumberFormatter.hexToLong(value)))))
  }

  def sendTransaction(transaction: Transaction) = {

    val futGasP = for (v <-
                         service
                           .asyncEthGasPrice
                           .future
                           .map(json => (json \ "result").extract[String])) yield v
    Await.result(futGasP, Duration(100, "seconds"))
    val objTransaction: EthSendTransactionObject =
      EthSendTransactionObject(
        from = transaction.from,
        to = Some(transaction.to),
        gas = Some(Utils.int2hex(1342177)),
        gasPrice = Some(futGasP.value.get.get),
        value = Some(Utils.long2hex(transaction.amount)),
        data = "",
        nonce = None
      )
    for (value <-
           service.asyncEthSendTransaction(objTransaction).future.map(json => (json \ "result").extract[String]))
      yield value.toString
  }

  def getTransactionCount(account: String) = {
    for (value <-
           service
             .asyncEthGetTransactionCount(account, BlockName("latest"))
             .future
             .map(json => (json \ "result").extract[String]))
      yield ToJson.toJson(IntegrationTransactionCount(List((account, NumberFormatter.hexToLong(value)))))
  }

}
