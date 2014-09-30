package com.laplacian.luxuryakka.core.jwt

import com.laplacian.luxuryakka.core.Asserts
import com.nimbusds.jose.crypto.{MACVerifier, MACSigner}

case class JwtSecret(secret : String)
{
  Asserts.argumentIsNotNullNorEmpty(secret)

  val signer   = new MACSigner(secret)
  val verifier = new MACVerifier(secret)
}
