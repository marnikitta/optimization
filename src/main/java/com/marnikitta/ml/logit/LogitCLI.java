package com.marnikitta.ml.logit;

import com.marnikitta.math.ArrayVector;
import com.marnikitta.math.Matrix;
import com.marnikitta.math.Vector;
import com.marnikitta.math.VectorRowsMatrix;
import com.marnikitta.ml.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LogitCLI {
  public static void main(String... args) throws IOException {
    final Method method = Method.valueOf(args[0].toUpperCase());
    if (method == Method.LEARN) {
      final String trainPath = args[1];
      final String modelPath = args[2];

      final List<String> lines = Files.readAllLines(Paths.get(trainPath));

      final List<Vector> features = new ArrayList<>();
      final double[] tArr = new double[lines.size()];

      for (int i = 0; i < lines.size(); i++) {
        final String[] split = lines.get(i).split("\t");
        final double[] entry = new double[split.length - 1];
        for (int j = 0; j < split.length - 1; j++) {
          entry[j] = Double.parseDouble(split[j]);
        }
        features.add(new ArrayVector(entry));
        tArr[i] = Double.parseDouble(split[split.length - 1]);
      }

      final Matrix feature = new VectorRowsMatrix(features);
      final Vector target = new ArrayVector(tArr);

      final LogitRegression logit = new LogitRegression(2);
      final LogitRegression.LogitModel fit = logit.fit(feature, target);

      Files.write(Paths.get(modelPath), fit.serialized());

    } else if (method == Method.CALC) {
      final String testPath = args[1];
      final String modelPath = args[2];
      final String outputPath = args[3];

      final byte[] modelParams = Files.readAllBytes(Paths.get(modelPath));

      final List<String> lines = Files.readAllLines(Paths.get(testPath));
      final List<Vector> features = new ArrayList<>();

      for (String line : lines) {
        final String[] split = line.split("\t");
        final double[] entry = new double[split.length];
        for (int j = 0; j < split.length; j++) {
          entry[j] = Double.parseDouble(split[j]);
        }
        features.add(new ArrayVector(entry));
      }

      final Matrix data = new VectorRowsMatrix(features);
      final Model model = new LogitRegression.LogitModel(modelParams);

      final Vector result = new ArrayVector(data.rows());
      model.batchPredict(data, result);

      Files.write(Paths.get(outputPath), result.toString().getBytes());
    }
  }

  private enum Method {
    LEARN,
    CALC
  }
}
