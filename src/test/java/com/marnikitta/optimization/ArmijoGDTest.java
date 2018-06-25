package com.marnikitta.optimization;

import com.marnikitta.math.Vector;
import com.marnikitta.math.functions.Parabolic;
import com.marnikitta.math.functions.Rosenbrock;
import com.marnikitta.optimization.first.ArmijoGD;
import com.marnikitta.optimization.first.FirstOrderOracle;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ArmijoGDTest {
  @Test
  public void testParabolic() {
    final FirstOrderOracle oracle = new Parabolic(1);
    final Vector start = new Vector(1, 7);
    new ArmijoGD().minimize(oracle, start);
    Assert.assertTrue(start.l2Norm() < 1.0e-4);
  }

  @Test
  public void testMultivarParabolic() {
    final int n = 1000;

    final FirstOrderOracle oracle = new Parabolic(n);
    final Vector start = new Vector(n, 1000);
    new ArmijoGD().minimize(oracle, start);
    Assert.assertTrue(start.l2Norm() < 1.0e-4, "Got: " + String.valueOf(start.l2Norm()));
  }

  @Test
  public void testRosenbrock() {
    final FirstOrderOracle oracle = new Rosenbrock(2);

    final Vector start = new Vector(2, -700);
    new ArmijoGD().minimize(oracle, start);
    Assert.assertTrue(start.l2Dist(new Vector(2, 1)) < 1.0e4);
  }

  @Test
  public void testMultivarRosenbrock() {
    final int n = 10000;

    final FirstOrderOracle oracle = new Rosenbrock(100);

    final Vector start = new Vector(n, -134);
    new ArmijoGD().minimize(oracle, start);
    Assert.assertTrue(Math.abs(oracle.func(start)) < 1.0e-6, "Got oracle: " + oracle.func(start));
  }
}
