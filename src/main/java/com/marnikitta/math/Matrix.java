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
      for (VectorIterator it = a.get(i).nonZeroIterator(); it.hasNext(); ) {
        it.advance();
        dest.set(it.position(), i, it.value());
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

    dest.clear();

    for (int i = 0; i < a.rows(); ++i) {
      for (VectorIterator aIt = a.get(i).nonZeroIterator(); aIt.hasNext(); ) {
        aIt.advance();
        final int k = aIt.position();
        final double s = aIt.value();

        for (VectorIterator bIt = b.get(k).nonZeroIterator(); bIt.hasNext(); ) {
          bIt.advance();
          final int j = bIt.position();
          dest.set(i, j, dest.get(i, j) + s * bIt.value());
        }
      }
    }
  }

  static void adjustDiag(Matrix a, double add, Matrix dest) {
    Assert.assertSame(a, dest);

    final int diagLength = Math.min(a.rows(), a.columns());
    for (int i = 0; i < diagLength; i++) {
      a.set(i, i, a.get(i, i) + add);
    }
  }

  static void multWithDiag(Matrix a, Vector diag, Matrix b, Matrix dest) {
    dest.clear();
    Assert.assertN(a, diag.length());

    for (int i = 0; i < a.rows(); ++i) {
      for (VectorIterator aIt = a.get(i).nonZeroIterator(); aIt.hasNext(); ) {
        aIt.advance();
        final int k = aIt.position();
        final double s = aIt.value() * diag.get(k);

        for (VectorIterator bIt = b.get(k).nonZeroIterator(); bIt.hasNext(); ) {
          bIt.advance();
          final int j = bIt.position();
          dest.set(i, j, dest.get(i, j) + s * bIt.value());
        }
      }
    }
  }
}
