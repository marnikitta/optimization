package com.marnikitta.math;

import com.marnikitta.math.util.Assert;

import java.util.Arrays;
import java.util.Objects;

public class ArrayMatrix implements Matrix {
  private double[][] data;
  private int m;
  private int n;

  public ArrayMatrix(int n) {
    this.data = new double[n][n];
    this.m = n;
    this.n = n;
  }

  public ArrayMatrix(int m, int n) {
    this.data = new double[m][m];
    this.m = m;
    this.n = n;
  }

  public ArrayMatrix(double[][] data) {
    this.data = data;
    m = data.length;
    n = data[0].length;
  }

  @Override
  public void clear() {
    for (int i = 0; i < m; ++i) {
      Arrays.fill(data[i], 0);
    }
  }

  @Override
  public double get(int i, int j) {
    return data[i][j];
  }

  @Override
  public void set(int i, int j, double value) {
    data[i][j] = value;
  }

  @Override
  public int m() {
    return m;
  }

  @Override
  public int n() {
    return n;
  }

  @Override
  public void mult(Vector vec, Vector dest) {
    Assert.assertLength(vec, n);
    Assert.assertLength(dest, m);

    for (int i = 0; i < m; ++i) {
      double tmp = 0;
      for (int j = 0; j < n; ++j) {
        tmp += data[i][j] * vec.get(j);
      }
      dest.set(i, tmp);
    }
  }

  @Override
  public void mult(Matrix that, Matrix dest) {
    Assert.assertM(that, n);
    Assert.assertM(dest, m);
    Assert.assertN(dest, that.n());

    for (int i = 0; i < m; ++i) {
      final double t = get(i, 0);
      for (int j = 0; j < that.n(); ++j) {
        dest.set(i, j, t * that.get(0, j));
      }

      for (int k = 1; k < n; ++k) {
        final double s = get(i, k);
        for (int j = 0; j < that.n(); ++j) {
          dest.set(i, j, dest.get(i, j) + s * that.get(k, j));
        }
      }
    }
  }

  @Override
  public void transpose(Matrix dest) {
    Assert.assertM(dest, n);
    Assert.assertN(dest, m);

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        dest.set(i, j, data[j][i]);
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ArrayMatrix matrix = (ArrayMatrix) o;
    return m == matrix.m() &&
      n == matrix.n() &&
      Arrays.deepEquals(data, matrix.data);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(m, n);
    result = 31 * result + Arrays.deepHashCode(data);
    return result;
  }

  @Override
  public String toString() {
    return Arrays.deepToString(data);
  }

  @Override
  public double maxAbsDiff(Matrix that) {
    Assert.assertSame(this, that);
    double result = 0;

    for (int j = 0; j < n; ++j) {
      for (int i = 0; i < m; ++i) {
        result = Math.max(result, Math.abs(get(i, j) - that.get(i, j)));
      }
    }

    return Math.sqrt(result);
  }

  @Override
  public ArrayMatrix copy() {
    final double[][] copy = new double[m][n];
    for (int i = 0; i < m; ++i) {
      System.arraycopy(data[i], 0, copy[i], 0, data[i].length);
    }
    return new ArrayMatrix(copy);
  }
}
