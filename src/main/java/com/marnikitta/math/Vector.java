package com.marnikitta.math;

import com.marnikitta.math.util.Assert;

import java.util.function.DoubleUnaryOperator;

public interface Vector {
  int length();

  double get(int index);

  void set(int i, double value);

  void clear();

  Vector copy();

  void copyTo(Vector dest);

  VectorIterator nonZeroIterator();

  default double sum() {
    double result = 0;
    for (VectorIterator it = nonZeroIterator(); it.hasNext(); ) {
      it.advance();
      result += it.value();
    }
    return result;
  }

  default void negate() {
    for (VectorIterator it = nonZeroIterator(); it.hasNext(); ) {
      it.advance();
      set(it.position(), -it.value());
    }
  }

  default double lpNormP(int p) {
    double result = 0;

    for (VectorIterator it = nonZeroIterator(); it.hasNext(); ) {
      it.advance();
      result += Math.pow(Math.abs(it.value()), p);
    }

    return result;
  }

  default double l2Norm() {
    return lpNorm(2);
  }

  default double l2Norm2() {
    return lpNormP(2);
  }

  default double lpNorm(int p) {
    return Math.pow(lpNormP(p), 1.0 / p);
  }

  static double l1Distance(Vector a, Vector b) {
    return lpDistance(a, b, 1);
  }

  static double lpDistance(Vector a, Vector b, int p) {
    return Math.pow(lpDistanceP(a, b, p), 1.0 / p);
  }

  static double lpDistanceP(Vector a, Vector b, int p) {
    Assert.assertSameLength(a, b);
    double result = 0;

    for (int i = 0; i < a.length(); ++i) {
      result += Math.pow(Math.abs(a.get(i) - b.get(i)), p);
    }

    return result;
  }

  static double dot(Vector a, Vector b) {
    Assert.assertSameLength(a, b);
    double result = 0;

    for (VectorIterator aIt = a.nonZeroIterator(); aIt.hasNext(); ) {
      aIt.advance();
      result += aIt.value() * b.get(aIt.position());
    }

    return result;
  }

  static void apply(Vector a, DoubleUnaryOperator f, Vector dest) {
    for (int i = 0; i < a.length(); i++) {
      dest.set(i, f.applyAsDouble(a.get(i)));
    }
  }

  static void mult(Vector a, double alpha, Vector dest) {
    Assert.assertSameLength(a, dest);
    dest.clear();

    for (VectorIterator it = a.nonZeroIterator(); it.hasNext(); ) {
      it.advance();
      dest.set(it.position(), it.value() * alpha);
    }
  }

  static void plus(Vector a, Vector b, Vector dest) {
    plus(a, 1, b, dest);
  }

  static void plus(Vector a, double alpha, Vector b, Vector dest) {
    Assert.assertSameLength(a, b);
    Assert.assertSameLength(a, dest);

    for (int i = 0; i < a.length(); ++i) {
      dest.set(i, a.get(i) + alpha * b.get(i));
    }
  }

  static double l2Distance(Vector a, Vector b) {
    return lpDistance(a, b, 2);
  }
}
