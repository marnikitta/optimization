package com.marnikitta.ml.logit;

import com.marnikitta.math.ArrayVector;
import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.math.VectorRowsMatrix;
import com.marnikitta.optimization.second.SecondOrderOracle;
import net.jcip.annotations.NotThreadSafe;

public class LogitRegression {

  @NotThreadSafe
  public static class Loss implements SecondOrderOracle {
    private final Matrix Z;
    private final Matrix ZT;
    private final double lambda;

    private Vector tmpM;
    private Vector tmpN;

    private Vector prevZw;
    private Vector prevw;

    public Loss(Matrix features, Vector target, double lambda) {
      Z = features.copy();
      this.lambda = lambda;
      Z.negate();
      Matrix.rowMult(Z, target, Z);

      ZT = new VectorRowsMatrix(Z.columns(), Z.rows());
      Matrix.transpose(Z, ZT);

      tmpM = new ArrayVector(features.rows());
      tmpN = new ArrayVector(features.columns());
      prevZw = new ArrayVector(features.rows());
      prevw = new ArrayVector(features.columns());
    }

    @Override
    public double func(Vector w) {
      if (!w.equals(prevw)) {
        Matrix.mult(Z, w, prevZw);
        w.copyTo(prevw);
      }
      Vector.apply(prevZw, x -> Math.log(1 + Math.exp(x)), tmpM);
      return tmpM.sum() + lambda / 2 * w.l2Norm2();
    }

    @Override
    public void grad(Vector w, Vector dest) {
      if (!w.equals(prevw)) {
        Matrix.mult(Z, w, prevZw);
        w.copyTo(prevw);
      }
      Vector.apply(prevZw, v -> 1.0 / (1 + Math.exp(-v)), tmpM);
      Matrix.mult(ZT, tmpM, tmpN);
      Vector.plus(tmpN, lambda, w, dest);
    }

    @Override
    public void hessian(Vector x, Matrix dst) {
      dst.clear();
    }
  }
}
