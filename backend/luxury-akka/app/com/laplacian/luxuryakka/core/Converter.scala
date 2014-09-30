package com.laplacian.luxuryakka.core

trait Converter[TIn, TOut]
{
  def convert(in :TIn): TOut
}
