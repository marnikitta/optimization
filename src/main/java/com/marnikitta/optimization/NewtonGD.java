package com.marnikitta.optimization;

import com.marnikitta.math.Vector;
import com.marnikitta.optimization.direction.NewtonDirection;
import com.marnikitta.optimization.step.ConstantStep;

import static com.marnikitta.optimization.LineSearch.DEFAULT_MAX_ITER;
import static com.marnikitta.optimization.LineSearch.DEFAULT_TOLERANCE;

public class NewtonGD {
  private final LineSearch<SecondOrderOracle> lineSearch;

  public NewtonGD() {
    this(DEFAULT_MAX_ITER, DEFAULT_TOLERANCE);
  }

  public NewtonGD(int maxIterations, double tolerance) {
    this.lineSearch = new LineSearch<>(new NewtonDirection(), new ConstantStep<>(1), maxIterations, tolerance);
  }

  public void minimize(SecondOrderOracle oracle, Vector start) {
    lineSearch.minimize(oracle, start);
  }
}
