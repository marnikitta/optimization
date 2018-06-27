package com.marnikitta.optimization.second;

import com.marnikitta.math.ArrayMatrix;
import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.math.algorithms.Factorization;
import com.marnikitta.math.algorithms.LinearSolve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewtonGD implements SecondOrderMinimizer {
  private static final int DEFAULT_MAX_ITER = (int) 1.0e6;
  private static final int DEFAULT_MAX_ORACLE_CALLS = (int) 1.0e8;
  private static final double DEFAULT_TOLERANCE = 1.0e-6;

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
    Vector grad = start.copy();
    Vector tmpD = start.copy();
    Vector d = start.copy();
    Matrix hessian = new ArrayMatrix(start.length());
    Matrix cholesky = new ArrayMatrix(start.length());
    Matrix choleskyT = new ArrayMatrix(start.length());

    final String header = String.format("%15s %15s %15s %15s\n", "iteration", "grad norm", "fxk", "oracleCalls");
    log.debug(header);

    int iteration = 0;
    int oracleCalls = 0;

    while (iteration < maxIterations && oracleCalls < maxOracleCalls) {
      oracleCalls += 1;
      final double f = oracle.func(start);
      oracle.grad(start, grad);
      oracle.hessian(start, hessian);

      final double gradNorm = grad.l2Norm();

      if (iteration % 1001 == 0 && log.isDebugEnabled()) {
        log.debug(String.format("%15d %15e %15e% 15d\n", iteration, gradNorm, f, oracleCalls));
      }

      if (Math.abs(gradNorm) < tolerance) {
        log.debug(String.format("%15d %15e %15e% 15d\n", iteration, gradNorm, f, oracleCalls));
        log.debug("Early exit due to low gradient norm: {}", gradNorm);
        break;
      }

      double tau = 0;
      while (!Factorization.cholesky(hessian, cholesky)) {
        for (int i = 0; i < start.length(); ++i) {
          hessian.set(i, i, hessian.get(i, i) + tau);
        }

        tau = Math.max(tau, tau * 2);
      }

      LinearSolve.rootsLower(cholesky, grad, tmpD);
      Matrix.transpose(cholesky, choleskyT);
      LinearSolve.rootsUpper(choleskyT, tmpD, d);

      Vector.plus(start, -1, d, start);

      iteration++;
    }
  }
}
