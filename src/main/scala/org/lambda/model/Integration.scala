package org.lambda.model

trait IntegrationResponse
case class IntegrationAccounts(accounts:List[String]) extends IntegrationResponse
case class IntegrationBlockNumber(blockNumber:Long) extends IntegrationResponse
case class IntegrationBalance(balances:List[(String, String)]) extends IntegrationResponse
case class IntegrationMinig(mining:Boolean) extends IntegrationResponse
case class IntegrationTransactionCount(transactions:List[(String, String)]) extends IntegrationResponse

trait IntegrationRequest
case class Transaction(from:String,
                       to:String,
                       amount:Long) extends IntegrationRequest