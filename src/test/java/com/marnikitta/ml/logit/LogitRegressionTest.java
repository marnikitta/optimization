package com.marnikitta.ml.logit;

import com.marnikitta.math.ArrayVector;
import com.marnikitta.math.MathRandom;
import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.math.VectorRowsMatrix;
import com.marnikitta.math.util.GradUtils;
import com.marnikitta.optimization.second.SecondOrderOracle;
import org.testng.annotations.Test;

public class LogitRegressionTest {
  private final SecondOrderOracle loss;
  private final int m = 1000;
  private final int n = 100;

  public LogitRegressionTest() {
    final MathRandom rd = new MathRandom(10);

    final Matrix X = new VectorRowsMatrix(m, n);
    final Vector y = new ArrayVector(m);
    rd.fill(X);
    rd.fill(y);

    this.loss = new LogitLoss(X, y, 0);
  }

  @Test
  public void testGrad() {
    GradUtils.checkGrad(loss, n, 100, 1);
  }

  @Test
  public void testHessian() {
    GradUtils.checkHessian(loss, n, 3, 1);
  }
}