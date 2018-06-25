package com.marnikitta.math;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MatrixTest {

  @Test
  public void testMult() {
    final Matrix a = new Matrix(new double[][]{
      {1, 2, 3},
      {4, 5, 6}
    });

    final Matrix b = new Matrix(new double[][]{
      {7, 8},
      {9, 10},
      {11, 12}
    });

    final Matrix expected = new Matrix(new double[][]{
      {58, 64},
      {139, 154}
    });

    final Matrix mult = new Matrix(2);
    a.mult(b, mult);

    Assert.assertEquals(mult, expected);
  }
}
