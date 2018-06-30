package com.marnikitta.ml.logit;

import com.marnikitta.math.ArrayVector;
import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.ml.Model;
import com.marnikitta.optimization.NewtonGD;

import java.nio.ByteBuffer;

public class LogitRegression {
  private final NewtonGD gr;
  private final double lambda;

  public LogitRegression(double lambda) {
    this.gr = new NewtonGD();
    this.lambda = lambda;
  }

  public LogitModel fit(Matrix features, Vector target) {
    final LogitLoss loss = new LogitLoss(features, target, lambda);
    final Vector start = new ArrayVector(features.columns(), 0);
    gr.minimize(loss, start);
    return new LogitModel(start);
  }

  public static class LogitModel implements Model {
    private final Vector w;

    public LogitModel(Vector w) {
      this.w = w;
    }

    public LogitModel(byte[] serialized) {
      final ByteBuffer wrap = ByteBuffer.wrap(serialized);
      final int len = wrap.limit() / Double.BYTES;
      final double[] m = new double[len];
      for (int i = 0; i < len; ++i) {
        m[i] = wrap.getDouble();
      }
      this.w = new ArrayVector(m);
    }

    @Override
    public double predict(Vector x) {
      return 1.0 / (1 + Math.exp(-Vector.dot(x, w)));
    }

    @Override
    public void batchPredict(Matrix x, Vector dest) {
      for (int i = 0; i < x.rows(); i++) {
        dest.set(i, predict(x.get(i)));
      }
    }

    @Override
    public byte[] serialized() {
      final ByteBuffer wrap = ByteBuffer.allocate(w.length() * Double.BYTES);
      for (int i = 0; i < w.length(); ++i) {
        wrap.putDouble(w.get(i));
      }
      return wrap.array();
    }
  }
}
