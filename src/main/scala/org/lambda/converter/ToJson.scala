package org.lambda.converter

import net.liftweb.json.Serialization.write
import org.lambda.model.IntegrationResponse

object ToJson {
  implicit val jsonFormats = net.liftweb.json.DefaultFormats
  def toJson(integrationResponse: IntegrationResponse) : String =
    write(integrationResponse)
}
