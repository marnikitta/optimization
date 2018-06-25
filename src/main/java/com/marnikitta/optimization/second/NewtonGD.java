package com.marnikitta.optimization.second;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.math.algorithms.Factorization;
import com.marnikitta.math.algorithms.LinearSolve;
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
    final Vector grad = new Vector(start.length());
    final Vector tmpD = new Vector(start.length());
    final Vector d = new Vector(start.length());
    final Matrix hessian = new Matrix(start.length());
    final Matrix cholesky = new Matrix(start.length());
    final Matrix choleskyT = new Matrix(start.length());

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
      cholesky.transpose(choleskyT);
      LinearSolve.rootsUpper(choleskyT, tmpD, d);

      start.plus(-1, d);

      iteration++;
    }
  }
}
