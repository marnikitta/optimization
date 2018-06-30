package com.marnikitta.optimization;

import com.marnikitta.math.Vector;

public interface FirstOrderOracle {
  double func(Vector x);

  void grad(Vector x, Vector dest);
}
