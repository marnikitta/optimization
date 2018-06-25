package com.marnikitta.math.util;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.optimization.first.FirstOrderOracle;
import com.marnikitta.optimization.second.SecondOrderOracle;

import java.util.Random;

public final class GradUtils {
  public static final double EPS1 = 1.0e-8;
  public static final double EPS2 = 1.0e-4;
  public static final double LEFT = -1;
  public static final double RIGHT = 1;

  private GradUtils() {}

  public static void checkGrad(FirstOrderOracle oracle, int dim, int pointsCount) {
    checkGrad(oracle, dim, pointsCount, EPS1);
  }

  public static void checkGrad(FirstOrderOracle oracle, int dim, int pointsCount, double eps) {
    final Vector grad = new Vector(dim);
    final Vector approxGrad = new Vector(dim);

    final Vector point = new Vector(dim);

    final Random rd = new Random(4);

    for (int i = 0; i < pointsCount; ++i) {
      for (int j = 0; j < dim; ++j) {
        point.set(j, rd.nextDouble() * (RIGHT - LEFT) - (RIGHT + LEFT) / 2);
      }

      for (int j = 0; j < dim; ++j) {
        final double value = point.get(j);
        point.plusAt(j, EPS1);
        final double fPlus = oracle.func(point);
        point.set(j, value);
        approxGrad.set(j, (fPlus - oracle.func(point)) / EPS1);
      }

      oracle.grad(point, grad);

      final double abs = grad.l1Dist(approxGrad);

      if (abs > eps) {
        throw new IllegalArgumentException("Grad is broken: difference is " + abs);
      }
    }
  }

  public static void checkHessian(SecondOrderOracle oracle, int dim, int pointsCount, double eps) {
    final Matrix hessian = new Matrix(dim);
    final Matrix approxHessian = new Matrix(dim);

    final Vector point = new Vector(dim);

    final Random rd = new Random();

    for (int n = 0; n < pointsCount; ++n) {
      for (int j = 0; j < dim; ++j) {
        point.set(j, rd.nextDouble() * (RIGHT - LEFT) - (RIGHT + LEFT) / 2);
      }

      for (int i = 0; i < dim; ++i) {
        for (int j = 0; j < dim; ++j) {
          double result = 0;

          final double xi = point.get(i);
          final double xj = point.get(j);

          point.plusAt(i, EPS2);
          point.plusAt(j, EPS2);
          result += oracle.func(point);
          point.set(i, xi);
          point.set(j, xj);

          point.plusAt(i, EPS2);
          result -= oracle.func(point);
          point.set(i, xi);

          point.plusAt(j, EPS2);
          result -= oracle.func(point);
          point.set(j, xj);

          result += oracle.func(point);

          approxHessian.set(i, j, result / (EPS2 * EPS2));
        }
      }

      oracle.hessian(point, hessian);

      final double abs = hessian.maxAbsDiff(approxHessian);

      if (abs > eps) {
        throw new IllegalArgumentException("Grad is broken: difference is " + abs);
      }
    }
  }
}
