package com.marnikitta.ml.logit;

import com.marnikitta.math.ArrayMatrix;
import com.marnikitta.math.ArrayVector;

import java.util.function.DoubleUnaryOperator;

public class LogitRegression {

  public Model fit(ArrayMatrix x, ArrayVector y) {
    return new Model();
  }

  public static class Model implements DoubleUnaryOperator {
    @Override
    public double applyAsDouble(double operand) {
      return 0;
    }
  }
}
