package com.marnikitta.math;

import com.marnikitta.math.util.Assert;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class Vector {
  private double[] data;

  public Vector(double[] value) {
    this.data = value;
  }

  public Vector(int length, double fill) {
    this.data = new double[length];
    Arrays.fill(data, fill);
  }

  public Vector(int length) {
    this.data = new double[length];
  }

  public int length() {
    return data.length;
  }

  public double get(int index) {
    return data[index];
  }

  public void set(int i, double value) {
    data[i] = value;
  }

  public void plusAt(int index, double value) {
    data[index] += value;
  }

  public void plus(double alpha, Vector other) {
    Assert.assertSameLength(this, other);

    for (int i = 0; i < length(); ++i) {
      set(i, get(i) + alpha * other.get(i));
    }
  }

  public void plus(double alpha, Vector other, Vector dst) {
    Assert.assertSameLength(this, other);
    Assert.assertSameLength(this, dst);

    for (int i = 0; i < length(); ++i) {
      dst.set(i, get(i) + alpha * other.get(i));
    }
  }

  public void mult(double coefficient, Vector dest) {
    Assert.assertSameLength(this, dest);

    for (int i = 0; i < length(); ++i) {
      dest.set(i, coefficient * get(i));
    }
  }

  public double l2Norm() {
    double result = 0;

    for (double d : data) {
      result += d * d;
    }

    return Math.sqrt(result);
  }

  public double l1Dist(Vector other) {
    double result = 0;

    for (int i = 0; i < data.length; i++) {
      final double v = data[i] - other.get(i);
      result += Math.abs(v);
    }

    return result;
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
    Assert.assertSameLength(this, other);
    double result = 0;

    for (int i = 0; i < length(); ++i) {
      result += get(i) * other.get(i);
    }

    return result;
  }

  public double sum(DoubleUnaryOperator f) {
    double result = 0;
    for (double d : data) {
      result += f.applyAsDouble(d);
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

  public void copy(Vector dst) {
    System.arraycopy(data, 0, dst.data, 0, data.length);
  }

  @Override
  public String toString() {
    return Arrays.toString(data);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Vector vector = (Vector) o;
    return Arrays.equals(data, vector.data);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(data);
  }
}
