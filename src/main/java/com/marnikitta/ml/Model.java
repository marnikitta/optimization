package com.marnikitta.ml;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;

public interface Model {
  double predict(Vector x);

  byte[] serialized();

  void batchPredict(Matrix data, Vector result);
}
