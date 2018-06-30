package com.marnikitta.optimization;

import com.marnikitta.math.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineSearch<T extends FirstOrderOracle> {
  private final Logger log = LoggerFactory.getLogger(LineSearch.class);

  public static final int DEFAULT_MAX_ITER = (int) 1.0e6;
  public static final double DEFAULT_TOLERANCE = 1.0e-7;

  private final Direction<T> dirSearcher;
  private final Step<T> step;
  private final int maxIterations;
  private final double tolerance;

  public LineSearch(Direction<T> dirSearcher,
                    Step<T> step,
                    int maxIterations,
                    double tolerance) {
    this.dirSearcher = dirSearcher;
    this.step = step;
    this.maxIterations = maxIterations;
    this.tolerance = tolerance;
  }

  public void minimize(T oracle, Vector start) {
    Vector grad = start.copy();
    Vector decentDirection = start.copy();

    final String header = String.format("%15s %15s %15s\n", "iteration", "grad norm", "fxk");
    log.debug(header);

    int iteration = 0;

    while (iteration < maxIterations) {
      final double f = oracle.func(start);
      oracle.grad(start, grad);

      final double gradNorm = grad.l2Norm();

      if (iteration % 2 == 0 && log.isDebugEnabled()) {
        log.debug(String.format("%15d %15e %15e\n", iteration, gradNorm, f));
      }

      if (Math.abs(gradNorm) < tolerance) {
        log.debug(String.format("%15d %15e %15e\n", iteration, gradNorm, f));
        log.debug("Early exit due to low gradient norm: {}", gradNorm);
        break;
      }

      dirSearcher.find(start, f, grad, oracle, decentDirection);
      final double step = this.step.stepSize(start, f, grad, decentDirection, oracle);

      Vector.plus(start, step, decentDirection, start);

      iteration++;
    }
  }

  public interface Direction<T extends FirstOrderOracle> {
    void find(Vector point, double value, Vector gradient, T oracle, Vector direction);
  }

  public interface Step<T extends FirstOrderOracle> {
    double stepSize(Vector point, double value, Vector gradient, Vector direction, T oracle);
  }
}
