package com.marnikitta.optimization;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.optimization.FirstOrderOracle;

public interface SecondOrderOracle extends FirstOrderOracle {
  void hessian(Vector x, Matrix dst);
}
