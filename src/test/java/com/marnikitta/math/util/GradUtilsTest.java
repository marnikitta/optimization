package com.marnikitta.math.util;

import com.marnikitta.math.functions.Rosenbrock;
import com.marnikitta.optimization.first.FirstOrderOracle;
import com.marnikitta.optimization.second.SecondOrderOracle;
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
  public void testGrad(FirstOrderOracle oracle, int dim) {
    GradUtils.checkGrad(oracle, dim, 100, 1);
  }

  @Test(dataProvider = "secondOrderOracles")
  public void testHessian(SecondOrderOracle oracle, int dim) {
    GradUtils.checkHessian(oracle, dim, 100, 1);
  }
}