package com.marnikitta.math.util;

import com.marnikitta.math.functions.Rosenbrock;
import com.marnikitta.optimization.first.FirstOrderOracle;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GradUtilsTest {

  @DataProvider
  public Object[][] secondOrderOracles() {
    return new Object[][]{
      {new Rosenbrock(2), 2},
      {new Rosenbrock(100), 100},
      };
  }

  @Test(dataProvider = "secondOrderOracles")
  public void testParabolicGrad(FirstOrderOracle oracle, int dim) {
    GradUtils.checkGrad(oracle, dim, 100, 1.0e-2);
  }
}