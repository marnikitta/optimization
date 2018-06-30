package com.marnikitta.math.util;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;

public class Assert {
  public static void assertM(Matrix matrix, int m) {
    if (matrix.rows() != m) {
      throw new IllegalArgumentException("Matrix hasn't desired height");
    }
  }

  public static void assertN(Matrix matrix, int n) {
    if (matrix.columns() != n) {
      throw new IllegalArgumentException("Matrix hasn't desired width");
    }
  }

  public static void assertLength(Vector vec, int length) {
    if (vec.length() != length) {
      throw new IllegalArgumentException("Vector hasn't desired length");
    }
  }

  public static void assertSquare(Matrix matrix) {
    if (matrix.rows() != matrix.columns()) {
      throw new IllegalArgumentException("Matrix should be square");
    }
  }

  public static void assertSame(Matrix a, Matrix b) {
    if (a.rows() != b.rows() || a.columns() != b.columns()) {
      throw new IllegalArgumentException("Matrices should have the same dimensions");
    }
  }

  public static void assertSameLength(Vector a, Vector b) {
    if (b.length() != a.length()) {
      throw new IllegalArgumentException("Other vector should have the same length");
    }
  }
}
