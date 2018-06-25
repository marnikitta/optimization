package com.marnikitta.math.algorithms;

import com.marnikitta.math.Matrix;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FactorizationTest {
  @Test
  public void testCholesky() {
    final Matrix a = new Matrix(new double[][]{
      {4, 12, -16},
      {12, 37, -43},
      {-16, -43, 98}
    });

    final Matrix result = new Matrix(a.n());
    Assert.assertTrue(Factorization.cholesky(a, result));

    final Matrix transposed = new Matrix(a.n());
    result.transpose(transposed);

    final Matrix mult = new Matrix(a.n());
    result.mult(transposed, mult);

    Assert.assertEquals(mult, a);
  }
}