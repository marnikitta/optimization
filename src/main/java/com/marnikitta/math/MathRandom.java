package com.marnikitta.math;

import java.util.Random;

public class MathRandom {
  private final Random rd;

  public MathRandom(Random rd) {
    this.rd = rd;
  }

  public MathRandom(long seed) {
    this.rd = new Random(seed);
  }

  public MathRandom() {
    this(new Random());
  }

  public void fill(Matrix dest) {
    for (int i = 0; i < dest.rows(); i++) {
      for (int j = 0; j < dest.columns(); j++) {
        dest.set(i, j, 10 * rd.nextDouble() - 5);
      }
    }
  }

  public void fill(Vector dest) {
    for (int i = 0; i < dest.length(); i++) {
      dest.set(i, 10 * rd.nextDouble() - 5);
    }
  }
}
