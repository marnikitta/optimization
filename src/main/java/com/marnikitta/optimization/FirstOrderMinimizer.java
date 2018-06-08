package com.marnikitta.optimization;

import com.marnikitta.math.Vector;

public interface FirstOrderMinimizer {
  Vector minimize(FirstOrderOracle firstOrderOracle, Vector start);
}
