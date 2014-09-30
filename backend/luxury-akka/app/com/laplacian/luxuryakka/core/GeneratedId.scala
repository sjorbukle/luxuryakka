package com.laplacian.luxuryakka.core

case class GeneratedId(id: Long)
{
  Asserts.argumentIsTrue(id > 0)
}
