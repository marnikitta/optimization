package com.marnikitta.math;

import com.marnikitta.math.util.Assert;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ArrayMatrix implements Matrix {
  private List<Vector> rows;
  private int m;
  private int n;

  public ArrayMatrix(int n) {
    this(n, n);
  }

  public ArrayMatrix(int m, int n) {
    this.rows = new ArrayList<>(m);
    for (int i = 0; i < m; ++i) {
      this.rows.add(i, new ArrayVector(n));
    }
    this.m = m;
    this.n = n;
  }

  public ArrayMatrix(double[][] data) {
    this.rows = new ArrayList<>(data.length);
    for (int i = 0; i < data.length; ++i) {
      this.rows.set(i, new ArrayVector(data[i]));
    }
    m = data.length;
    n = data[0].length;
  }

  public ArrayMatrix(Collection<Vector> rows) {
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
  public void set(int i, int j, double value) {
    rows.get(i).set(j, value);
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
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ArrayMatrix vectors = (ArrayMatrix) o;
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
  public ArrayMatrix copy() {
    final List<Vector> copy = new ArrayList<>(m);
    for (int i = 0; i < m; ++i) {
      copy.add(i, rows.get(i).copy());
    }
    return new ArrayMatrix(copy);
  }

  @NotNull
  @Override
  public Iterator<Vector> iterator() {
    return rows.iterator();
  }
}
