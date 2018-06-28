package com.marnikitta.math;

import org.testng.Assert;
import org.testng.annotations.Test;

public class VectorRowsMatrixTest {

  @Test
  public void testMult() {
    final VectorRowsMatrix a = new VectorRowsMatrix(new double[][]{
      {1, 2, 3},
      {4, 5, 6}
    });

    final VectorRowsMatrix b = new VectorRowsMatrix(new double[][]{
      {7, 8},
      {9, 10},
      {11, 12}
    });

    final VectorRowsMatrix expected = new VectorRowsMatrix(new double[][]{
      {58, 64},
      {139, 154}
    });

    final VectorRowsMatrix mult = new VectorRowsMatrix(2);
    Matrix.mult(a, b, mult);

    Assert.assertEquals(mult, expected);
  }
}
