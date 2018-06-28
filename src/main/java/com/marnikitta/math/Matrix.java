package com.marnikitta.math;

import com.marnikitta.math.util.Assert;

public interface Matrix extends Iterable<Vector> {
  void clear();

  int rows();

  int columns();

  double get(int i, int j);

  Vector get(int i);

  void set(int i, int j, double value);

  Matrix copy();

  double maxAbsDiff(Matrix that);

  default void negate() {
    forEach(Vector::negate);
  }

  static void transpose(Matrix a, Matrix dest) {
    Assert.assertM(dest, a.columns());
    Assert.assertN(dest, a.rows());

    for (int i = 0; i < a.rows(); i++) {
      for (int j = 0; j < a.columns(); j++) {
        dest.set(j, i, a.get(i, j));
      }
    }
  }

  static void mult(Matrix a, Vector b, Vector dest) {
    Assert.assertLength(b, a.columns());
    Assert.assertLength(dest, a.rows());

    int i = 0;
    for (Vector row : a) {
      dest.set(i, Vector.dot(row, b));
      i++;
    }
  }

  static void rowMult(Matrix a, Vector y, Matrix dest) {
    Assert.assertLength(y, a.rows());

    for (int i = 0; i < a.rows(); ++i) {
      Vector.mult(a.get(i), y.get(i), dest.get(i));
    }
  }

  static void mult(Matrix a, Matrix b, Matrix dest) {
    Assert.assertM(b, a.columns());
    Assert.assertM(dest, a.rows());
    Assert.assertN(dest, b.columns());

    for (int i = 0; i < a.rows(); ++i) {
      final double t = a.get(i, 0);
      for (int j = 0; j < b.columns(); ++j) {
        dest.set(i, j, t * b.get(0, j));
      }

      for (int k = 1; k < a.columns(); ++k) {
        final double s = a.get(i, k);
        for (int j = 0; j < b.columns(); ++j) {
          dest.set(i, j, dest.get(i, j) + s * b.get(k, j));
        }
      }
    }
  }
}
