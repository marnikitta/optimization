package com.marnikitta.optimization.first;

import com.marnikitta.math.Vector;

public interface FirstOrderMinimizer {
  void minimize(FirstOrderOracle oracle, Vector start);
}
