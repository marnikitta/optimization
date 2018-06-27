package com.marnikitta.math;

import com.marnikitta.math.util.Assert;

public interface Matrix extends Iterable<Vector> {
  void clear();

  double get(int i, int j);

  void set(int i, int j, double value);

  int m();

  int n();

  Matrix copy();

  double maxAbsDiff(Matrix that);

  static void transpose(Matrix a, Matrix dest) {
    Assert.assertM(dest, a.n());
    Assert.assertN(dest, a.m());

    for (int i = 0; i < a.m(); i++) {
      for (int j = 0; j < a.n(); j++) {
        dest.set(i, j, a.get(j, i));
      }
    }
  }

  static void mult(Matrix a, Vector b, Vector dest) {
    Assert.assertLength(b, a.n());
    Assert.assertLength(dest, a.m());

    int i = 0;
    for (Vector row : a) {
      dest.set(i, Vector.dot(row, b));
      i++;
    }
  }

  static void mult(Matrix a, Matrix b, Matrix dest) {
    Assert.assertM(b, a.n());
    Assert.assertM(dest, a.m());
    Assert.assertN(dest, b.n());

    for (int i = 0; i < a.m(); ++i) {
      final double t = a.get(i, 0);
      for (int j = 0; j < b.n(); ++j) {
        dest.set(i, j, t * b.get(0, j));
      }

      for (int k = 1; k < a.n(); ++k) {
        final double s = a.get(i, k);
        for (int j = 0; j < b.n(); ++j) {
          dest.set(i, j, dest.get(i, j) + s * b.get(k, j));
        }
      }
    }
  }
}
