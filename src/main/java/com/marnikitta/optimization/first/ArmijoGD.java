package com.marnikitta.optimization.first;

import com.marnikitta.math.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmijoGD implements FirstOrderMinimizer {
  private static final int DEFAULT_MAX_ITER = (int) 1.0e6;
  private static final int DEFAULT_MAX_ORACLE_CALLS = (int) 1.0e8;
  private static final double DEFAULT_TOLERANCE = 1.0e-5;
  private static final double DEFAULT_C1 = 1.0e-6;

  private final Logger log = LoggerFactory.getLogger(ArmijoGD.class);

  private final int maxIterations;
  private final int maxOracleCalls;
  private final double tolerance;
  private final double c1;

  public ArmijoGD() {
    this(DEFAULT_MAX_ITER, DEFAULT_MAX_ORACLE_CALLS, DEFAULT_TOLERANCE, DEFAULT_C1);
  }

  public ArmijoGD(int maxIterations, int maxOracleCalls, double tolerance, double c1) {
    this.maxIterations = maxIterations;
    this.maxOracleCalls = maxOracleCalls;
    this.tolerance = tolerance;
    this.c1 = c1;
  }

  @Override
  public void minimize(FirstOrderOracle oracle, Vector start) {
    Vector grad = start.copy();

    Vector armijoAttempt = start.copy();

    final String header = String.format("%15s %15s %15s %15s\n", "iteration", "grad norm", "fxk", "oracleCalls");
    log.debug(header);

    int iteration = 0;
    int oracleCalls = 0;

    double alpha = 1;

    while (iteration < maxIterations && oracleCalls < maxOracleCalls) {
      final double f = oracle.func(start);
      oracle.grad(start, grad);
      oracleCalls++;

      final double gradNorm = grad.l2Norm();

      if (iteration % 1001 == 0 && log.isDebugEnabled()) {
        log.debug(String.format("%15d %15e %15e% 15d\n", iteration, gradNorm, f, oracleCalls));
      }

      if (Math.abs(gradNorm) < tolerance) {
        log.debug(String.format("%15d %15e %15e% 15d\n", iteration, gradNorm, f, oracleCalls));
        log.debug("Early exit due to low gradient norm: {}", gradNorm);
        break;
      }

      // Armijo
      alpha *= 2;
      final double dDiff = -grad.l2Norm2();

      Vector.plus(start, -alpha, grad, armijoAttempt);
      double armijoTest = oracle.func(armijoAttempt);
      oracleCalls++;

      while (armijoTest > f + c1 * alpha * dDiff) {
        oracleCalls++;
        alpha /= 2;

        Vector.plus(start, -alpha, grad, armijoAttempt);
        armijoTest = oracle.func(armijoAttempt);
      }

      armijoAttempt.copyTo(start);
      iteration++;
    }
  }
}
