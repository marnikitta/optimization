package com.marnikitta.math.functions;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.optimization.SecondOrderOracle;

import static java.lang.Math.pow;

public class Rosenbrock implements SecondOrderOracle {
  private final int n;

  public Rosenbrock(int n) {
    this.n = n;
  }

  @Override
  public void hessian(Vector x, Matrix dst) {
    dst.clear();
    for (int i = 0; i < n / 2; ++i) {
      final double x2i = x.get(2 * i);
      final double x2i1 = x.get(2 * i + 1);
      dst.set(2 * i, 2 * i, 1200 * x2i * x2i - 400 * x2i1 + 2);
      dst.set(2 * i, 2 * i + 1, -400 * x2i);
      dst.set(2 * i + 1, 2 * i, -400 * x2i);
      dst.set(2 * i + 1, 2 * i + 1, 200);
    }
  }

  @Override
  public double func(Vector x) {
    double result = 0;

    for (int i = 0; i < n / 2; i++) {
      result += 100 * pow(pow(x.get(2 * i), 2) - x.get(2 * i + 1), 2) + pow(x.get(2 * i) - 1, 2);
    }

    return result;
  }

  @Override
  public void grad(Vector x, Vector dst) {
    for (int i = 0; i < n / 2; i++) {
      dst.set(2 * i, 400 * x.get(2 * i) * (pow(x.get(2 * i), 2) - x.get(2 * i + 1)) + 2 * (x.get(2 * i) - 1));
      dst.set(2 * i + 1, -200 * (pow(x.get(2 * i), 2) - x.get(2 * i + 1)));
    }
  }
}
