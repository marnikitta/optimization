package com.marnikitta.math;

public interface Vector {
  int length();

  double get(int index);

  void set(int i, double value);

  void plus(double alpha, Vector other, Vector dst);

  void mult(double coefficient, Vector dest);

  double l2Norm2();

  double l2Norm();

  double l1Distance(Vector other);

  double l2Distance(Vector other);

  double dot(Vector other);

  Vector copy();

  void copyTo(Vector dst);
}
