package com.marnikitta.ml.logit;

import com.marnikitta.math.ArrayVector;
import com.marnikitta.math.MathRandom;
import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.math.VectorRowsMatrix;
import com.marnikitta.math.util.GradUtils;
import com.marnikitta.optimization.SecondOrderOracle;
import org.testng.annotations.Test;

public class LogitRegressionTest {
  @Test
  public void testGrad() {
    GradUtils.checkGrad(randomLoss(1000, 100), 100, 100, 1);
  }

  @Test
  public void testHessian() {
    GradUtils.checkHessian(randomLoss(1000, 100), 100, 3, 1);
  }

  private SecondOrderOracle randomLoss(int m, int n) {
    final MathRandom rd = new MathRandom(10);

    final Matrix X = new VectorRowsMatrix(m, n);
    final Vector y = new ArrayVector(m);
    rd.fill(X);
    rd.fill(y);

    return new LogitLoss(X, y, 1.0 / m);
  }
}