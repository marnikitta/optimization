package com.marnikitta.optimization.second;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.optimization.first.FirstOrderOracle;

public interface SecondOrderOracle extends FirstOrderOracle {
  void hessian(Vector x, Matrix dst);
}
