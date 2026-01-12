package es.uma.informatica.misia.ae.simpleea.common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CsvWriter {

  private static final String SEPARATOR = ";";
  private static final String LINE_BREAK = "\n";

  public static void writeResultsGA(int file, int n, int p, double populationSize,
      double mutationProb, double fitness) throws IOException {

    Path path = Paths.get("src", "main", "java", "es", "uma", "informatica", "misia", "ae", "simpleea", "data",
        "resultsGA.csv");
    boolean existe = Files.exists(path);

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile(), true))) {

      if (!existe) {
        bw.write("File;N;P;Population_Size;Mutation_Prob;Fitness");
        bw.write(LINE_BREAK);
      }

      bw.write(
          file + SEPARATOR +
              n + SEPARATOR +
              p + SEPARATOR +
              populationSize + SEPARATOR +
              mutationProb + SEPARATOR +
              fitness);
      bw.write(LINE_BREAK);
    }
  }

  public static void writeResultsSA(int file, int n, int p, double initialTemp, double reduction, double fitness)
      throws IOException {

    Path path = Paths.get("src", "main", "java", "es", "uma", "informatica", "misia", "ae", "simpleea", "data",
        "resultsSA.csv");
    boolean existe = Files.exists(path);

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile(), true))) {

      if (!existe) {
        bw.write("File;N;P;Initial_Temperature;Reduction_coef;Fitness");
        bw.write(LINE_BREAK);
      }

      bw.write(
          file + SEPARATOR +
              n + SEPARATOR +
              p + SEPARATOR +
              initialTemp + SEPARATOR +
              reduction + SEPARATOR +
              fitness);
      bw.write(LINE_BREAK);
    }
  }

  public static void writeResultsGAvsSA(String algorithm, int file, int n, int p, double fitness, long time)
      throws IOException {

    Path path = Paths.get("src", "main", "java", "es", "uma", "informatica", "misia", "ae", "simpleea", "data",
        "resultsGAvsSA.csv");
    boolean existe = Files.exists(path);

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(path.toFile(), true))) {

      if (!existe) {
        bw.write("algorithm;File;N;P;Fitness;Time");
        bw.write(LINE_BREAK);
      }

      bw.write(
          algorithm + SEPARATOR +
              file + SEPARATOR +
              n + SEPARATOR +
              p + SEPARATOR +
              fitness + SEPARATOR +
              time);
      bw.write(LINE_BREAK);
    }
  }
}
