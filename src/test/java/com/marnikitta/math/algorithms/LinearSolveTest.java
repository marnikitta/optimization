package com.marnikitta.math.algorithms;

import com.marnikitta.math.ArrayMatrix;
import com.marnikitta.math.ArrayVector;
import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LinearSolveTest {

  @Test
  public void testRootsLower() {
    final Matrix a = new ArrayMatrix(new double[][]{
      {1, 0, 0},
      {2, 3, 0},
      {4, 5, 6}
    });

    final Vector expected = new ArrayVector(new double[]{
      7,
      8,
      9
    });

    final Vector b = new ArrayVector(3);
    a.mult(expected, b);

    final Vector actual = new ArrayVector(3);
    LinearSolve.rootsLower(a, b, actual);
    Assert.assertEquals(actual, expected);
  }

  @Test
  public void testRootsUpper() {
    final Matrix a = new ArrayMatrix(new double[][]{
      {1, 2, 3},
      {0, 4, 5},
      {0, 0, 6}
    });

    final Vector expected = new ArrayVector(new double[]{
      7,
      8,
      9
    });

    final Vector b = new ArrayVector(3);
    a.mult(expected, b);

    final Vector actual = new ArrayVector(3);
    LinearSolve.rootsUpper(a, b, actual);
    Assert.assertEquals(actual, expected);
  }
}