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
    Assert.assertSameLength(this, other);
    Assert.assertSameLength(this, dest);

    for (int i = 0; i < length(); ++i) {
      dest.set(i, get(i) + other.get(i));
    }
  }

  public Vector plus(Vector other) {
    Assert.assertSameLength(this, other);

    for (int i = 0; i < length(); ++i) {
      data[i] += other.get(i);
    }

    return this;
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
