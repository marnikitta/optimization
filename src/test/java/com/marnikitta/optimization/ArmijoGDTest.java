package com.marnikitta.optimization;

import com.marnikitta.math.Vector;
import org.testng.Assert;
import org.testng.annotations.Test;

import static java.lang.StrictMath.pow;

public class ArmijoGDTest {
  @Test
  public void testParabolic() {
    final FirstOrderOracle oracle = new FirstOrderOracle() {
      @Override
      public double func(Vector x) {
        return x.get(0) * x.get(0);
      }

      @Override
      public void grad(Vector x, Vector dst) {
        dst.set(0, 2 * x.get(0));
      }
    };

    final Vector minimize = new ArmijoGD().minimize(oracle, new Vector(1, 7));
    Assert.assertTrue(minimize.l2Dist(new Vector(1, 0)) < 1.0e-6);
  }

  @Test
  public void testRosenbrock() {
    final FirstOrderOracle oracle = new FirstOrderOracle() {
      @Override
      public double func(Vector vec) {
        final double x = vec.get(0);
        final double y = vec.get(1);
        return pow(1.0 - x, 2) + 100.0 * pow(y - x * x, 2);
      }

      @Override
      public void grad(Vector vec, Vector dst) {
        final double x = vec.get(0);
        final double y = vec.get(1);
        dst.set(0, 2.0 * (x - 1.0) + 400.0 * x * (x * x - y));
        dst.set(1, 200.0 * (y - x * x));
      }
    };

    final Vector minimize = new ArmijoGD().minimize(oracle, new Vector(2, -700));
    Assert.assertTrue(minimize.l2Dist(new Vector(2, 1)) < 1.0e-6);
  }

  @Test
  public void testMultivarRosenbrock() {
    final int n = 100;

    final FirstOrderOracle oracle = new FirstOrderOracle() {
      @Override
      public double func(Vector x) {
        double result = 0;

        for (int i = 0; i < n / 2; i++) {
          result += 100 * pow(pow(x.get(2 * i), 2) - x.get(2 * i + 1), 2) + pow(x.get(2 * i) - 1, 2);
        }

        return result;
      }

      @Override
      public void grad(Vector x, Vector dst) {
        for (int i = 0; i < n / 2; i++) {
          dst.set(2 * i, 400 * x.get(2 * i) * (pow(x.get(2 * i), 2) - x.get(2 * i + 1)) + 2 * (x.get(2 * i) - 1));
          dst.set(2 * i + 1, -200 * (pow(x.get(2 * i), 2) - x.get(2 * i + 1)));
        }
      }
    };

    final Vector argmin = new ArmijoGD().minimize(oracle, new Vector(n, 19));
    Assert.assertTrue(Math.abs(oracle.func(argmin)) < 1.0e-6, "Got oracle: " + oracle.func(argmin));
  }
}
