package com.marnikitta.optimization.second;

import com.marnikitta.math.Vector;

public interface SecondOrderMinimizer {
  void minimize(SecondOrderOracle oracle, Vector start);
}
