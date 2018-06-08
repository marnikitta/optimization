package com.marnikitta.math;

import com.marnikitta.optimization.first.FirstOrderOracle;

import java.util.Random;

public final class Utils {
  public static final double EPS = 1.0e-8;
  public static final double EPS_GRAD = 1.0e-4;
  public static final double LEFT = -1;
  public static final double RIGHT = 1;

  private Utils() {}

  public static void checkGrad(FirstOrderOracle oracle, int dim, int pointsCount) {
    final Vector grad = new Vector(dim);
    final Vector approxGrad = new Vector(dim);

    final Vector point = new Vector(dim);
    final Vector h = new Vector(dim);
    final Vector e = new Vector(dim);

    final Random rd = new Random(4);

    for (int i = 0; i < pointsCount; ++i) {
      for (int j = 0; j < dim; ++j) {
        point.set(j, rd.nextDouble() * (RIGHT - LEFT) - (RIGHT + LEFT) / 2);
      }

      for (int j = 0; j < dim; ++j) {
        e.set(j, EPS);
        point.plus(e, h);
        approxGrad.set(j, (oracle.func(h) - oracle.func(point)) / EPS);
        e.set(j, 0);
      }

      oracle.grad(point, grad);

      final double abs = grad.l2Dist(approxGrad);

      if (abs > EPS_GRAD) {
        throw new IllegalArgumentException("Grad is broken: difference is " + abs);
      }
    }
  }
}
