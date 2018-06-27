package com.marnikitta.math;

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
  public void clear() {
    Arrays.fill(data, 0);
  }

  @Override
  public ArrayVector copy() {
    final double[] copy = Arrays.copyOf(data, data.length);
    return new ArrayVector(copy);
  }

  @Override
  public void copyTo(Vector dest) {
    if (dest instanceof ArrayVector) {
      final ArrayVector arrayDst = ((ArrayVector) dest);
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
