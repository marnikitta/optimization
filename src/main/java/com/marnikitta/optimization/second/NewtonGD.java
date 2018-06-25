package com.marnikitta.optimization.second;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewtonGD implements SecondOrderMinimizer {
  public static final int DEFAULT_MAX_ITER = (int) 1.0e6;
  public static final int DEFAULT_MAX_ORACLE_CALLS = (int) 1.0e8;
  public static final double DEFAULT_TOLERANCE = 1.0e-6;

  private final Logger log = LoggerFactory.getLogger(NewtonGD.class);

  private final int maxIterations;
  private final int maxOracleCalls;
  private final double tolerance;

  public NewtonGD() {
    this(DEFAULT_MAX_ITER, DEFAULT_MAX_ORACLE_CALLS, DEFAULT_TOLERANCE);
  }

  public NewtonGD(int maxIterations, int maxOracleCalls, double tolerance) {
    this.maxIterations = maxIterations;
    this.maxOracleCalls = maxOracleCalls;
    this.tolerance = tolerance;
  }

  @Override
  public void minimize(SecondOrderOracle oracle, Vector start) {
    final Vector point = start;
    final Vector grad = new Vector(start.length());
    final Matrix hessian = new Matrix(start.length());

    int iteration = 0;
    int oracleCalls = 0;

    while (iteration < maxIterations && oracleCalls < maxOracleCalls) {
      final double f = oracle.func(start);
      oracle.grad(start, grad);
      oracle.hessian(start, hessian);

      iteration++;
    }
  }
}
