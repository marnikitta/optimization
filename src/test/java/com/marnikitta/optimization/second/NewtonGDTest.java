package com.marnikitta.optimization.second;

import com.marnikitta.math.Vector;
import com.marnikitta.math.functions.Parabolic;
import com.marnikitta.math.functions.Rosenbrock;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NewtonGDTest {
  @Test
  public void testParabolic() {
    final int n = 1000;

    final SecondOrderOracle oracle = new Parabolic(n);
    final Vector start = new Vector(n, 1000);
    new NewtonGD().minimize(oracle, start);
    Assert.assertTrue(start.l2Norm() < 1.0e-4, "Got: " + String.valueOf(start.l2Norm()));
  }

  @Test
  public void testRosenbrock() {
    final int n = 1000;

    final SecondOrderOracle oracle = new Rosenbrock(n);
    final Vector start = new Vector(n, 1111111);
    new NewtonGD().minimize(oracle, start);
    Assert.assertTrue(start.l2Dist(new Vector(n, 1)) < 1.0e-4, "Got: " + String.valueOf(start.l2Norm()));
  }
}