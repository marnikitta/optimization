package com.marnikitta.math;

public interface Matrix {
  void clear();

  double get(int i, int j);

  void set(int i, int j, double value);

  int m();

  int n();

  void mult(Vector vec, Vector dst);

  void mult(Matrix that, Matrix dest);

  void transpose(Matrix dest);

  double maxAbsDiff(Matrix that);

  Matrix copy();
}
