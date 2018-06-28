package com.marnikitta.ml.logit;

import com.marnikitta.math.ArrayVector;
import com.marnikitta.math.MathRandom;
import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.math.VectorRowsMatrix;
import com.marnikitta.math.util.GradUtils;
import org.testng.annotations.Test;

public class LogitRegressionTest {
  private final MathRandom rd = new MathRandom(10);

  @Test
  public void testLoss() {
    final int m = 1000;
    final int n = 100;

    final Matrix X = new VectorRowsMatrix(m, n);
    final Vector y = new ArrayVector(m);
    rd.fill(X);
    rd.fill(y);

    final LogitLoss logitLoss = new LogitLoss(X, y, 4);

    GradUtils.checkGrad(logitLoss, n, 100, 1);
  }
}