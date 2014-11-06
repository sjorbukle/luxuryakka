package com.laplacian.luxuryakka.core.utils

import com.laplacian.luxuryakka.core.Asserts
import play.api.libs.Codecs


object HashUtils
{
  val salt = "jdhk4:AGla5765G6.,GR453u(%)(A435=gskgjde4)=(#%)(gsdgksj<468)"
  val numberOfPasses = 20

  def sha1(value: String) : String =
  {
    Asserts.argumentIsNotNull(value)

    var result = value
    for(i <- 1 to numberOfPasses) {
      result = calculateSha1Digest(result)
    }

    result
  }

  private def calculateSha1Digest(value: String) : String = Codecs.sha1(salt + value)
}

