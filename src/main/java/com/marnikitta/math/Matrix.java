package com.marnikitta.math;

import java.util.function.DoubleUnaryOperator;

public class Matrix {
  private double[][] data = new double[0][0];
  private int m = 0;
  private int n = 0;

  public Matrix wrap(double[][] data) {
    this.data = data;
    m = data.length;
    n = data[0].length;
    return this;
  }

  public double get(int i, int j) {
    return data[i][j];
  }

  public void set(int i, int j, double value) {
    data[i][j] = value;
  }

  public int m() {
    return m;
  }

  public int n() {
    return n;
  }

  public Matrix apply(DoubleUnaryOperator function, Matrix dst) {
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        dst.set(i, j, function.applyAsDouble(data[i][j]));
      }
    }
    return dst;
  }

  public Matrix apply(DoubleUnaryOperator function) {
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        set(i, j, function.applyAsDouble(data[i][j]));
      }
    }
    return this;
  }

  public Vector mult(Vector vec, Vector dst) {
    assertLength(vec, n);
    assertLength(dst, m);

    for (int i = 0; i < m; ++i) {
      double tmp = 0;
      for (int j = 0; j < n; ++j) {
        tmp += data[i][j] * vec.get(j);
      }
      dst.set(i, tmp);
    }

    return dst;
  }

  public Matrix transpose(Matrix dst) {
    assertM(dst, n);
    assertN(dst, m);

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        dst.set(i, j, data[j][i]);
      }
    }

    return dst;
  }

  private void assertM(Matrix matrix, int m) {
    if (matrix.m() != m) {
      throw new IllegalArgumentException("Matrix hasn't desired height");
    }
  }

  private void assertN(Matrix matrix, int n) {
    if (matrix.n() != n) {
      throw new IllegalArgumentException("Matrix hasn't desired width");
    }
  }

  private void assertLength(Vector vec, int length) {
    if (vec.length() != length) {
      throw new IllegalArgumentException("Vector hasn't desired length");
    }
  }
}
