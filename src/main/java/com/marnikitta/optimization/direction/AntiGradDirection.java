package com.marnikitta.optimization.direction;

import com.marnikitta.math.Vector;
import com.marnikitta.optimization.FirstOrderOracle;
import com.marnikitta.optimization.LineSearch;

public class AntiGradDirection implements LineSearch.Direction<FirstOrderOracle> {
  @Override
  public void find(Vector point, double value, Vector gradient, FirstOrderOracle oracle, Vector direction) {
    Vector.mult(gradient, -1, direction);
  }
}
