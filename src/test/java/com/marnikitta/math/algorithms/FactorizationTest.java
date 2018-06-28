package com.marnikitta.math.algorithms;

import com.marnikitta.math.VectorRowsMatrix;
import com.marnikitta.math.Matrix;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FactorizationTest {
  @Test
  public void testCholesky() {
    final Matrix a = new VectorRowsMatrix(new double[][]{
      {4, 12, -16},
      {12, 37, -43},
      {-16, -43, 98}
    });

    final Matrix result = a.copy();
    Assert.assertTrue(Factorization.cholesky(a, result));

    final Matrix transposed = new VectorRowsMatrix(a.columns());
    Matrix.transpose(result, transposed);

    final Matrix mult = new VectorRowsMatrix(a.columns());
    Matrix.mult(result, transposed, mult);

    Assert.assertEquals(mult, a);
  }
}