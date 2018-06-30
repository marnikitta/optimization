package com.marnikitta.optimization;

import com.marnikitta.math.ArrayVector;
import com.marnikitta.math.Vector;
import com.marnikitta.math.functions.Parabolic;
import com.marnikitta.math.functions.Rosenbrock;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ArmijoGDTest {
  @Test
  public void testParabolic() {
    final FirstOrderOracle oracle = new Parabolic(1);
    final Vector start = new ArrayVector(1, 7);
    new ArmijoGD().minimize(oracle, start);
    Assert.assertTrue(start.l2Norm() < 1.0e-4);
  }

  @Test
  public void testMultivarParabolic() {
    final int n = 1000;

    final FirstOrderOracle oracle = new Parabolic(n);
    final Vector start = new ArrayVector(n, 100);
    new ArmijoGD().minimize(oracle, start);
    Assert.assertTrue(start.l2Norm() < 1.0e-4, "Got: " + String.valueOf(start.l2Norm()));
  }

  @Test
  public void testRosenbrock() {
    final FirstOrderOracle oracle = new Rosenbrock(2);

    final Vector start = new ArrayVector(2, -70);
    new ArmijoGD().minimize(oracle, start);
    Assert.assertTrue(Vector.l2Distance(start, new ArrayVector(2, 1)) < 1.0e4);
  }

  @Test
  public void testMultivarRosenbrock() {
    final int n = 10000;

    final FirstOrderOracle oracle = new Rosenbrock(n);

    final Vector start = new ArrayVector(n, -114);
    new ArmijoGD().minimize(oracle, start);
    Assert.assertTrue(Vector.l2Distance(start, new ArrayVector(n, 1)) < 1.0e-4, "Got oracle: " + oracle.func(start));
  }
}
