package com.marnikitta.optimization.step;

import com.marnikitta.math.Vector;
import com.marnikitta.optimization.LineSearch;
import com.marnikitta.optimization.FirstOrderOracle;

public class ArmijoStep implements LineSearch.Step<FirstOrderOracle> {
  private final double c1;

  private double alpha = 1;
  private Vector armijoAttempt;

  public ArmijoStep(double c1) {
    this.c1 = c1;
  }

  @Override
  public double stepSize(Vector point, double value, Vector gradient, Vector direction, FirstOrderOracle oracle) {
    if (armijoAttempt == null) {
      armijoAttempt = point.copy();
    }

    alpha *= 2;
    final double dDiff = Vector.dot(gradient, direction);

    Vector.plus(point, alpha, direction, armijoAttempt);
    double armijoTest = oracle.func(armijoAttempt);

    while (armijoTest > value + c1 * alpha * dDiff) {
      alpha /= 2;

      Vector.plus(point, alpha, direction, armijoAttempt);
      armijoTest = oracle.func(armijoAttempt);
    }

    return alpha;
  }
}
