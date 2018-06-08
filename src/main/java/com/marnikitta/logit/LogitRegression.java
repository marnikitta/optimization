package com.marnikitta.logit;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.optimization.first.FirstOrderOracle;

import java.util.function.DoubleUnaryOperator;

public class LogitRegression {

  public Model fit(Matrix x, Vector y) {
    return new Model();
  }

  public static class Model implements DoubleUnaryOperator {
    @Override
    public double applyAsDouble(double operand) {
      return 0;
    }
  }

  public static class LossOracle implements FirstOrderOracle {
    private final Matrix x;
    private final Vector y;

    public LossOracle(Matrix x, Vector y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public double func(Vector x) {
      return 0;
    }

    @Override
    public void grad(Vector x, Vector dst) {
    }
  }
}
