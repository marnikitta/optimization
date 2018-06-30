package com.marnikitta.math.functions;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.optimization.SecondOrderOracle;

public class Parabolic implements SecondOrderOracle {
  private final int n;

  public Parabolic(int n) {
    this.n = n;
  }

  @Override
  public void hessian(Vector x, Matrix dst) {
    dst.clear();
    for (int i = 0; i < n; ++i) {
      dst.set(i, i, i % 5 + 1);
    }
  }

  @Override
  public double func(Vector x) {
    double result = 0;
    for (int i = 0; i < n; ++i) {
      result += 0.5 * (i % 5 + 1) * x.get(i) * x.get(i);
    }
    return result;
  }

  @Override
  public void grad(Vector x, Vector dst) {
    for (int i = 0; i < n; ++i) {
      dst.set(i, (i % 5 + 1) * x.get(i));
    }
  }
}
