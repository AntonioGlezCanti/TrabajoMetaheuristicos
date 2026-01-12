package es.uma.informatica.misia.ae.simpleea.common;

import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Data {

  public static final double C = 3;

  public static final double T = 0.75;

  public static final double D = 2;

  public static final List<Path> PATHS = List.of(
      Paths.get("src", "main", "java", "es", "uma", "informatica", "misia", "ae", "simpleea", "data", "10-2.txt"),
      Paths.get("src", "main", "java", "es", "uma", "informatica", "misia", "ae", "simpleea", "data", "20-3.txt"),
      Paths.get("src", "main", "java", "es", "uma", "informatica", "misia", "ae", "simpleea", "data", "30-4.txt"),
      Paths.get("src", "main", "java", "es", "uma", "informatica", "misia", "ae", "simpleea", "data", "40-4.txt"),
      Paths.get("src", "main", "java", "es", "uma", "informatica", "misia", "ae", "simpleea", "data", "50-5.txt"));

  public static final int[] P = { 2, 3, 4, 5 };

  public static final double[] POPULATION_SIZES = { 10, 50, 100, 150, 200 };

  public static final double[] MUTATION_PROBS = { 0.01, 0.05, 0.1, 0.2 };

  public static final int[] TEMP = { 100, 1000 };

  public static final double[] REDUCTION = { 0.85, 0.9, 0.95, 0.97 };
}
