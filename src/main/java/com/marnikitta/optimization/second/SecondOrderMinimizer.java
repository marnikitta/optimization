package com.marnikitta.optimization.second;

import com.marnikitta.math.Vector;

public interface SecondOrderMinimizer {
  Vector minimize(SecondOrderOracle oracle, Vector start);
}
