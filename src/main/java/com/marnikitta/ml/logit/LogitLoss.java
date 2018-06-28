package com.marnikitta.ml.logit;

import com.marnikitta.math.ArrayVector;
import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.math.VectorRowsMatrix;
import com.marnikitta.math.util.Assert;
import com.marnikitta.optimization.second.SecondOrderOracle;
import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class LogitLoss implements SecondOrderOracle {
  private final Matrix Z;
  private final Matrix ZT;
  private final double lambda;
  private final double norm;

  private Vector tmpM;
  private Vector tmpN;

  private Vector prevZw;
  private Vector prevw;

  public LogitLoss(Matrix features, Vector target, double lambda) {
    Assert.assertM(features, target.length());

    Z = features.copy();
    this.lambda = lambda;
    Z.negate();
    Matrix.rowMult(Z, target, Z);

    norm = 1.0 / Z.rows();

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
    Vector.apply(prevZw, this::stablelogexp, tmpM);
    return norm * tmpM.sum() + lambda / 2 * w.l2Norm2();
  }

  private double stablelogexp(double x) {
    if (x < 0) {
      return Math.log(1 + Math.exp(x));
    } else {
      return x + Math.log(1 + Math.exp(-x));
    }
  }

  @Override
  public void grad(Vector w, Vector dest) {
    if (!w.equals(prevw)) {
      Matrix.mult(Z, w, prevZw);
      w.copyTo(prevw);
    }
    Vector.apply(prevZw, v -> norm / (1 + Math.exp(-v)), tmpM);
    Matrix.mult(ZT, tmpM, tmpN);
    Vector.plus(tmpN, lambda, w, dest);
  }

  @Override
  public void hessian(Vector x, Matrix dst) {
    dst.clear();
  }
}
