package com.marnikitta.math.util;

public interface DoubleIterator {
  boolean hasNext();

  void advance();

  double value();
}
