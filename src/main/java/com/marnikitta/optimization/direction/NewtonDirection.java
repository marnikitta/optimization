package com.marnikitta.optimization.direction;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.math.VectorRowsMatrix;
import com.marnikitta.math.algorithms.Factorization;
import com.marnikitta.math.algorithms.LinearSolve;
import com.marnikitta.optimization.LineSearch;
import com.marnikitta.optimization.SecondOrderOracle;

public class NewtonDirection implements LineSearch.Direction<SecondOrderOracle> {
  private Matrix hessian;
  private Matrix cholesky;
  private Matrix choleskyT;
  private Vector tmpD;

  @Override
  public void find(Vector point, double value, Vector gradient, SecondOrderOracle oracle, Vector direction) {
    if (hessian == null) {
      hessian = new VectorRowsMatrix(point.length());
      cholesky = new VectorRowsMatrix(point.length());
      choleskyT = new VectorRowsMatrix(point.length());
      tmpD = point.copy();
    }

    oracle.hessian(point, hessian);

    double tau = 0;
    while (!Factorization.cholesky(hessian, cholesky)) {
      for (int i = 0; i < point.length(); ++i) {
        Matrix.adjustDiag(hessian, tau, hessian);
      }

      tau = Math.max(tau, tau * 2);
    }

    LinearSolve.rootsLower(cholesky, gradient, tmpD);
    tmpD.negate();
    Matrix.transpose(cholesky, choleskyT);
    LinearSolve.rootsUpper(choleskyT, tmpD, direction);
  }
}
