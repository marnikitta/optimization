package com.marnikitta.math.algorithms;

import com.marnikitta.math.Matrix;
import com.marnikitta.math.util.Assert;

public class Factorization {
  public static boolean cholesky(Matrix a, Matrix dst) {
    Assert.assertSquare(a);
    Assert.assertSame(a, dst);
    dst.clear();

    for (int i = 0; i < a.columns(); ++i) {
      double s = 0;
      for (int p = 0; p < i; ++p) {
        final double tmp = dst.get(i, p);
        s += tmp * tmp;
      }
      s = a.get(i, i) - s;
      if (s <= 0) {
        return false;
      }
      dst.set(i, i, Math.sqrt(s));

      for (int j = i + 1; j < a.columns(); ++j) {
        double t = 0;
        for (int p = 0; p < i; ++p) {
          t += dst.get(i, p) * dst.get(j, p);
        }
        dst.set(j, i, (a.get(j, i) - t) / dst.get(i, i));
      }
    }

    return true;
  }
}
