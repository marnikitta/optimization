package com.marnikitta.optimization.step;

import com.marnikitta.math.Vector;
import com.marnikitta.optimization.FirstOrderOracle;
import com.marnikitta.optimization.LineSearch;

public class ConstantStep<T extends FirstOrderOracle> implements LineSearch.Step<T> {
  private final double step;

  public ConstantStep(double step) {
    this.step = step;
  }

  @Override
  public double stepSize(Vector point, double value, Vector gradient, Vector direction, T oracle) {
    return step;
  }
}
