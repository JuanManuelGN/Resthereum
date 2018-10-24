package org.lambda.converter

import org.web3scala.exception.QuantityDecodingException

object NumberFormatter {

  def hexToLong(hex:String): String = {
    if (isValidHex(hex))
      hexToBigIntString(hex.substring(2))
    else
      throw new QuantityDecodingException("Invalid hex value")
  }

  private def hexToBigIntString(s: String): String =
    s
      .toList
      .map("0123456789abcdef".indexOf(_))
      .map(BigInt(_))
      .reduceLeft(_ * 16 + _).toString

  private val isValidHex = (value:String) => {
    if( (value == null) ||
      (value.length < 3) ||
      (value.charAt(0) != '0') ||
      (value.charAt(1) != 'x')||
      (value.length > 3 && value.charAt(2) == '0'))
      false
    else
      true
  }
}
