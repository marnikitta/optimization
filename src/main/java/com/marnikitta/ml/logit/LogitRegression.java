package com.marnikitta.ml.logit;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.optimization.second.SecondOrderOracle;

public class LogitRegression {

  private static class Loss implements SecondOrderOracle {
    @Override
    public void hessian(Vector x, Matrix dst) {
      dst.clear();
    }

    @Override
    public double func(Vector x) {
      return 0;
    }

    @Override
    public void grad(Vector x, Vector dst) {
      dst.clear();
    }
  }
}
