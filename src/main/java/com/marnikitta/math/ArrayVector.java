package com.marnikitta.math;

import com.marnikitta.math.util.Assert;

import java.util.Arrays;

public class ArrayVector implements Vector {
  private double[] data;

  public ArrayVector(double[] value) {
    this.data = value;
  }

  public ArrayVector(int length, double fill) {
    this.data = new double[length];
    Arrays.fill(data, fill);
  }

  public ArrayVector(int length) {
    this.data = new double[length];
  }

  @Override
  public int length() {
    return data.length;
  }

  @Override
  public double get(int index) {
    return data[index];
  }

  @Override
  public void set(int i, double value) {
    data[i] = value;
  }

  @Override
  public void plus(double alpha, Vector other, Vector dst) {
    Assert.assertSameLength(this, other);
    Assert.assertSameLength(this, dst);

    for (int i = 0; i < length(); ++i) {
      dst.set(i, get(i) + alpha * other.get(i));
    }
  }

  @Override
  public void mult(double coefficient, Vector dest) {
    Assert.assertSameLength(this, dest);

    for (int i = 0; i < length(); ++i) {
      dest.set(i, coefficient * get(i));
    }
  }

  @Override
  public double l2Norm2() {
    double result = 0;

    for (double d : data) {
      result += d * d;
    }

    return Math.sqrt(result);
  }

  @Override
  public double l2Norm() {
    return Math.sqrt(l2Norm2());
  }

  @Override
  public double l1Distance(Vector other) {
    double result = 0;

    for (int i = 0; i < data.length; i++) {
      final double v = data[i] - other.get(i);
      result += Math.abs(v);
    }

    return result;
  }

  @Override
  public double l2Distance(Vector other) {
    double result = 0;

    for (int i = 0; i < data.length; i++) {
      final double v = data[i] - other.get(i);
      result += v * v;
    }

    return Math.sqrt(result);
  }

  @Override
  public double dot(Vector other) {
    Assert.assertSameLength(this, other);
    double result = 0;

    for (int i = 0; i < length(); ++i) {
      result += get(i) * other.get(i);
    }

    return result;
  }

  @Override
  public ArrayVector copy() {
    final double[] copy = Arrays.copyOf(data, data.length);
    return new ArrayVector(copy);
  }

  @Override
  public void copyTo(Vector dst) {
    if (dst instanceof ArrayVector) {
      final ArrayVector arrayDst = ((ArrayVector) dst);
      System.arraycopy(data, 0, arrayDst.data, 0, data.length);
    } else {
      throw new UnsupportedOperationException();
    }
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
    final ArrayVector vector = (ArrayVector) o;
    return Arrays.equals(data, vector.data);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(data);
  }
}
