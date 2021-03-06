package com.marnikitta.math;

import com.marnikitta.math.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class VectorRowsMatrix implements Matrix {
  private List<Vector> rows;
  private int m;
  private int n;

  public VectorRowsMatrix(int n) {
    this(n, n);
  }

  public VectorRowsMatrix(int m, int n, boolean sparse) {
    this.rows = new ArrayList<>(m);
    for (int i = 0; i < m; ++i) {
      if (sparse) {
        this.rows.add(i, new SparseVector(n));
      } else {
        this.rows.add(i, new ArrayVector(n));
      }
    }
    this.m = m;
    this.n = n;
  }

  public VectorRowsMatrix(int m, int n) {
    this(m, n, false);
  }

  public VectorRowsMatrix(double[][] data) {
    this.rows = new ArrayList<>(data.length);
    for (int i = 0; i < data.length; ++i) {
      this.rows.add(i, new ArrayVector(data[i]));
    }
    m = data.length;
    n = data[0].length;
  }

  public VectorRowsMatrix(Collection<Vector> rows) {
    this.rows = new ArrayList<>(rows);
    this.m = this.rows.size();
    this.n = this.rows.get(0).length();
  }

  @Override
  public void clear() {
    for (Vector row : rows) {
      row.clear();
    }
  }

  @Override
  public double get(int i, int j) {
    return rows.get(i).get(j);
  }

  @Override
  public Vector get(int i) {
    return rows.get(i);
  }

  @Override
  public void set(int i, int j, double value) {
    rows.get(i).set(j, value);
  }

  @Override
  public int rows() {
    return m;
  }

  @Override
  public int columns() {
    return n;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final VectorRowsMatrix vectors = (VectorRowsMatrix) o;
    return m == vectors.m &&
      n == vectors.n &&
      Objects.equals(rows, vectors.rows);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rows, m, n);
  }

  @Override
  public String toString() {
    return rows.toString();
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
  public VectorRowsMatrix copy() {
    final List<Vector> copy = new ArrayList<>(m);
    for (int i = 0; i < m; ++i) {
      copy.add(i, rows.get(i).copy());
    }
    return new VectorRowsMatrix(copy);
  }

  @Override
  public Iterator<Vector> iterator() {
    return rows.iterator();
  }
}
