package com.marnikitta.math;

import com.marnikitta.math.util.Assert;

import java.util.Arrays;
import java.util.Objects;

public class Matrix {
  private double[][] data;
  private int m;
  private int n;

  public Matrix(int n) {
    this.data = new double[n][n];
    this.m = n;
    this.n = n;
  }

  public Matrix(int m, int n) {
    this.data = new double[m][m];
    this.m = m;
    this.n = n;
  }

  public Matrix(double[][] data) {
    this.data = data;
    m = data.length;
    n = data[0].length;
  }

  public void clear() {
    for (int i = 0; i < m; ++i) {
      Arrays.fill(data[i], 0);
    }
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

  public void mult(Vector vec, Vector dst) {
    Assert.assertLength(vec, n);
    Assert.assertLength(dst, m);

    for (int i = 0; i < m; ++i) {
      double tmp = 0;
      for (int j = 0; j < n; ++j) {
        tmp += data[i][j] * vec.get(j);
      }
      dst.set(i, tmp);
    }
  }

  public void mult(Matrix that, Matrix dst) {
    Assert.assertM(that, n);
    Assert.assertM(dst, m);
    Assert.assertN(dst, that.n());

    for (int i = 0; i < m; ++i) {
      final double t = get(i, 0);
      for (int j = 0; j < that.n(); ++j) {
        dst.set(i, j, t * that.get(0, j));
      }

      for (int k = 1; k < n; ++k) {
        final double s = get(i, k);
        for (int j = 0; j < that.n(); ++j) {
          dst.set(i, j, dst.get(i, j) + s * that.get(k, j));
        }
      }
    }
  }

  public void transpose(Matrix dst) {
    Assert.assertM(dst, n);
    Assert.assertN(dst, m);

    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        dst.set(i, j, data[j][i]);
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
    final Matrix matrix = (Matrix) o;
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
}
