package com.marnikitta.math;

import gnu.trove.iterator.TIntDoubleIterator;
import gnu.trove.map.TIntDoubleMap;
import gnu.trove.map.hash.TIntDoubleHashMap;

public class SparseVector implements Vector {
  private final TIntDoubleMap data;
  private final int length;

  public SparseVector(int length) {
    this.data = new TIntDoubleHashMap();
    this.length = length;
  }

  public SparseVector(double[] data) {
    this.data = new TIntDoubleHashMap();
    for (int i = 0; i < data.length; ++i) {
      if (data[i] != 0) {
        this.data.put(i, data[i]);
      }
    }
    length = data.length;
  }

  private SparseVector(TIntDoubleMap values, int length) {
    this.data = values;
    this.length = length;
  }

  @Override
  public int length() {
    return length;
  }

  @Override
  public double get(int index) {
    assertBounds(index);
    if (data.containsKey(index)) {
      return data.get(index);
    }
    return 0;
  }

  @Override
  public void set(int i, double value) {
    assertBounds(i);
    data.put(i, value);
  }

  @Override
  public void clear() {
    data.clear();
  }

  @Override
  public Vector copy() {
    return new SparseVector(new TIntDoubleHashMap(data), length);
  }

  @Override
  public void copyTo(Vector dest) {
    dest.clear();
    data.forEachEntry((a, b) -> {
      dest.set(a, b);
      return true;
    });
  }

  @Override
  public VectorIterator nonZeroIterator() {
    return new SparseNonZeroItearator();
  }

  private void assertBounds(int index) {
    if (index < 0 || index > length) {
      throw new IndexOutOfBoundsException();
    }
  }

  private class SparseNonZeroItearator implements VectorIterator {
    private final TIntDoubleIterator iterator = data.iterator();

    @Override
    public int position() {
      return iterator.key();
    }

    @Override
    public boolean hasNext() {
      return iterator.hasNext();
    }

    @Override
    public void advance() {
      iterator.advance();
    }

    @Override
    public double value() {
      return iterator.key();
    }

  }
}
