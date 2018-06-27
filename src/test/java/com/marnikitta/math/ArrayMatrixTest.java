package com.marnikitta.math;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ArrayMatrixTest {

  @Test
  public void testMult() {
    final ArrayMatrix a = new ArrayMatrix(new double[][]{
      {1, 2, 3},
      {4, 5, 6}
    });

    final ArrayMatrix b = new ArrayMatrix(new double[][]{
      {7, 8},
      {9, 10},
      {11, 12}
    });

    final ArrayMatrix expected = new ArrayMatrix(new double[][]{
      {58, 64},
      {139, 154}
    });

    final ArrayMatrix mult = new ArrayMatrix(2);
    Matrix.mult(a, b, mult);

    Assert.assertEquals(mult, expected);
  }
}
