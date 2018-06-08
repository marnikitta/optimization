package com.marnikitta.optimization.first;

import com.marnikitta.math.Vector;

public interface FirstOrderMinimizer {
  Vector minimize(FirstOrderOracle oracle, Vector start);
}
