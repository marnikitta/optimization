package com.marnikitta.math;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class Vector {
  private double[] data;

  private Vector(double[] value) {
    this.data = value;
  }

  public Vector(int length, double fill) {
    this.data = new double[length];
    Arrays.fill(data, fill);
  }

  public Vector(int length) {
    this.data = new double[length];
  }

  public Vector wrap(double[] data) {
    this.data = data;
    return this;
  }

  public int length() {
    return data.length;
  }

  public double get(int index) {
    return data[index];
  }

  public Vector set(int i, double value) {
    data[i] = value;
    return this;
  }

  public Vector apply(DoubleUnaryOperator function, Vector dst) {
    for (int i = 0; i < data.length; i++) {
      dst.set(i, function.applyAsDouble(data[i]));
    }
    return dst;
  }

  public Vector apply(DoubleUnaryOperator function) {
    for (int i = 0; i < data.length; i++) {
      set(i, function.applyAsDouble(data[i]));
    }
    return this;
  }

  public void plus(Vector other, Vector dest) {
    assertSameLength(other);
    assertSameLength(dest);

    for (int i = 0; i < length(); ++i) {
      dest.set(i, get(i) + other.get(i));
    }
  }

  public Vector plus(Vector other) {
    assertSameLength(other);

    for (int i = 0; i < length(); ++i) {
      data[i] += other.get(i);
    }

    return this;
  }

  public void plus(double alpha, Vector other, Vector dst) {
    assertSameLength(other);
    assertSameLength(dst);

    for (int i = 0; i < length(); ++i) {
      dst.set(i, get(i) + alpha * other.get(i));
    }
  }

  public void mult(double coefficient, Vector dest) {
    assertSameLength(dest);

    for (int i = 0; i < length(); ++i) {
      dest.set(i, coefficient * get(i));
    }
  }

  public Vector mult(double coefficient) {
    for (int i = 0; i < length(); ++i) {
      data[i] *= coefficient;
    }

    return this;
  }

  public double l2Norm() {
    double result = 0;

    for (double d : data) {
      result += d * d;
    }

    return Math.sqrt(result);
  }

  public double l2Dist(Vector other) {
    double result = 0;

    for (int i = 0; i < data.length; i++) {
      final double v = data[i] - other.get(i);
      result += v * v;
    }

    return Math.sqrt(result);
  }

  public double dot(Vector other) {
    assertSameLength(other);
    double result = 0;

    for (int i = 0; i < length(); ++i) {
      result += get(i) * other.get(i);
    }

    return result;
  }

  public double sum() {
    double result = 0;
    for (double d : data) {
      result += d;
    }
    return result;
  }

  public Vector copy() {
    return new Vector(Arrays.copyOf(data, data.length));
  }

  private void assertSameLength(Vector other) {
    if (other.length() != length()) {
      throw new IllegalArgumentException("Other vector should have the same length");
    }
  }

  @Override
  public String toString() {
    return Arrays.toString(data);
  }
}
