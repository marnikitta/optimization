package com.marnikitta.math.algorithms;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.math.util.Assert;

public class LinearSolve {
  public static void rootsLower(Matrix a, Vector b, Vector dest) {
    Assert.assertSquare(a);
    Assert.assertLength(b, a.rows());
    for (int i = 0; i < a.rows(); ++i) {
      double residual = 0;
      for (int j = 0; j < i; ++j) {
        residual += a.get(i, j) * dest.get(j);
      }

      if (a.get(i, i) == 0) {
        throw new IllegalArgumentException("Matrix should be positive definite");
      }

      dest.set(i, (b.get(i) - residual) / a.get(i, i));
    }
  }

  public static void rootsUpper(Matrix a, Vector b, Vector dest) {
    Assert.assertSquare(a);
    Assert.assertLength(b, a.rows());
    for (int i = a.rows() - 1; i >= 0; --i) {
      double residual = 0;
      for (int j = a.rows() - 1; j > i; --j) {
        residual += a.get(i, j) * dest.get(j);
      }

      if (a.get(i, i) == 0) {
        throw new IllegalArgumentException("Matrix should be positive definite");
      }

      dest.set(i, (b.get(i) - residual) / a.get(i, i));
    }
  }
}
