package com.marnikitta.optimization;

import com.marnikitta.math.Vector;
import com.marnikitta.optimization.direction.AntiGradDirection;
import com.marnikitta.optimization.step.ArmijoStep;

import static com.marnikitta.optimization.LineSearch.DEFAULT_MAX_ITER;
import static com.marnikitta.optimization.LineSearch.DEFAULT_TOLERANCE;

public class ArmijoGD {
  public static final double DEFAULT_C1 = 1.0e-6;

  private final LineSearch<FirstOrderOracle> lineSearch;

  public ArmijoGD() {
    this(DEFAULT_MAX_ITER, DEFAULT_TOLERANCE, DEFAULT_C1);
  }

  public ArmijoGD(int maxIterations, double tolerance, double c1) {
    this.lineSearch = new LineSearch<>(new AntiGradDirection(), new ArmijoStep(c1), maxIterations, tolerance);
  }

  public void minimize(FirstOrderOracle oracle, Vector start) {
    lineSearch.minimize(oracle, start);
  }
}
