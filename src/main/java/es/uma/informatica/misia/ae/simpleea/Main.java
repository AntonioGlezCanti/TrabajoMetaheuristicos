package es.uma.informatica.misia.ae.simpleea;

import es.uma.informatica.misia.ae.simpleea.evolutionaryAlgorithm.*;
import es.uma.informatica.misia.ae.simpleea.simulatedAnnealing.*;
import es.uma.informatica.misia.ae.simpleea.common.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

  public static void main(String[] args) throws IOException {
    // testGA();
    // testGABigProblem();
    // testSA();
    // testSABigProblem();

    // Run GA vs SA comparison 4 times
    for (int i = 0; i < 4; i++) {
      testGAvsSA();
    }
  }

  private static void testGA() throws IOException {
    for (int n = 1; n <= Data.PATHS.size(); n++) {
      for (int p = 0; p < Data.P.length; p++) {
        for (int populationIndex = 0; populationIndex < Data.POPULATION_SIZES.length; populationIndex++) {
          for (int probIndex = 0; probIndex < Data.MUTATION_PROBS.length; probIndex++) {
            PHub pHub = new PHub(n * 10, Data.P[p], Data.C, Data.T, Data.D, Data.PATHS.get(n - 1));
            Map<String, Double> parameters = getParameters(populationIndex, probIndex);
            EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(parameters, pHub);
            Individual bestSolution = ea.run();

            CsvWriter.writeResultsGA(n, n * 10, Data.P[p],
                Data.POPULATION_SIZES[populationIndex], Data.MUTATION_PROBS[probIndex],
                bestSolution.getFitness());
          }
        }
      }
    }
  }

  private static void testGABigProblem() throws IOException {
    int n = 200;
    int p = 8;
    PHub pHub = new PHub(n, p, Data.C, Data.T, Data.D,
        Paths.get("src", "main", "java", "es", "uma", "informatica", "misia", "ae", "simpleea", "data", "200-8.txt"));

    for (int populationIndex = 0; populationIndex < Data.POPULATION_SIZES.length; populationIndex++) {
      for (int probIndex = 0; probIndex < Data.MUTATION_PROBS.length; probIndex++) {
        Map<String, Double> parameters = getParameters(populationIndex, probIndex);
        EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(parameters, pHub);
        Individual bestSolution = ea.run();

        CsvWriter.writeResultsGA(6, n, p,
            Data.POPULATION_SIZES[populationIndex], Data.MUTATION_PROBS[probIndex],
            bestSolution.getFitness());
      }
    }
  }

  private static void testSA() throws IOException {
    for (int n = 1; n <= Data.PATHS.size(); n++) {
      for (int p = 0; p < Data.P.length; p++) {
        for (int tempIndex = 0; tempIndex < Data.TEMP.length; tempIndex++) {
          for (int reductionIndex = 0; reductionIndex < Data.REDUCTION.length; reductionIndex++) {
            PHub pHub = new PHub(n * 10, Data.P[p], Data.C, Data.T, Data.D, Data.PATHS.get(n - 1));
            SimulatedAnnealing sa = new SimulatedAnnealing(pHub);
            PHubSolution bestSolution = sa.run(Data.TEMP[tempIndex], Data.REDUCTION[reductionIndex]);

            CsvWriter.writeResultsSA(n, n * 10, Data.P[p], Data.TEMP[tempIndex], Data.REDUCTION[reductionIndex],
                bestSolution.getFitness());
          }
        }
      }
    }
  }

  private static void testSABigProblem() throws IOException {
    int n = 200;
    for (int p = 8; p <= 10; p++) {
      PHub pHub = new PHub(n, p, Data.C, Data.T, Data.D,
          Paths.get("src", "main", "java", "es", "uma", "informatica", "misia", "ae", "simpleea", "data", "200-8.txt"));
      SimulatedAnnealing sa = new SimulatedAnnealing(pHub);

      for (int tempIndex = 0; tempIndex < Data.TEMP.length; tempIndex++) {
        for (int reductionIndex = 0; reductionIndex < Data.REDUCTION.length; reductionIndex++) {
          PHubSolution bestSolution = sa.run(Data.TEMP[tempIndex], Data.REDUCTION[reductionIndex]);
          CsvWriter.writeResultsSA(n, n, p, Data.TEMP[tempIndex], Data.REDUCTION[reductionIndex],
              bestSolution.getFitness());
        }
      }
    }
  }

  private static void testGAvsSA() throws IOException {
    runGAComparison(1, Data.PATHS.size(), Data.P);
    runGABigProblemComparison(8, 10);

    runSAComparison(1, Data.PATHS.size(), Data.P);
    runSABigProblemComparison(8, 10);
  }

  private static void runGAComparison(int nStart, int nEnd, int[] PValues) throws IOException {
    for (int n = nStart; n <= nEnd; n++) {
      for (int p = 0; p < PValues.length; p++) {
        PHub pHub = new PHub(n * 10, PValues[p], Data.C, Data.T, Data.D, Data.PATHS.get(n - 1));
        Map<String, Double> parameters = getParameters(4, 1);
        EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(parameters, pHub);

        long startTime = System.nanoTime();
        Individual bestSolution = ea.run();
        long elapsedTime = (System.nanoTime() - startTime) / 1_000_000;

        CsvWriter.writeResultsGAvsSA("GA", n, n * 10, PValues[p], bestSolution.getFitness(), elapsedTime);
      }
    }
  }

  private static void runGABigProblemComparison(int pStart, int pEnd) throws IOException {
    int n = 200;
    for (int p = pStart; p <= pEnd; p++) {
      PHub pHub = new PHub(n, p, Data.C, Data.T, Data.D,
          Paths.get("src", "main", "java", "es", "uma", "informatica", "misia", "ae", "simpleea", "data", "200-8.txt"));
      Map<String, Double> parameters = getParameters(4, 1);
      EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(parameters, pHub);

      long startTime = System.nanoTime();
      Individual bestSolution = ea.run();
      long elapsedTime = (System.nanoTime() - startTime) / 1_000_000;

      CsvWriter.writeResultsGAvsSA("GA", 6, n, p, bestSolution.getFitness(), elapsedTime);
    }
  }

  private static void runSAComparison(int nStart, int nEnd, int[] PValues) throws IOException {
    for (int n = nStart; n <= nEnd; n++) {
      for (int p = 0; p < PValues.length; p++) {
        PHub pHub = new PHub(n * 10, PValues[p], Data.C, Data.T, Data.D, Data.PATHS.get(n - 1));
        SimulatedAnnealing sa = new SimulatedAnnealing(pHub);

        long startTime = System.nanoTime();
        PHubSolution bestSolution = sa.run(Data.TEMP[1], Data.REDUCTION[1]);
        long elapsedTime = (System.nanoTime() - startTime) / 1_000_000;

        CsvWriter.writeResultsGAvsSA("SA", n, n * 10, PValues[p], bestSolution.getFitness(), elapsedTime);
      }
    }
  }

  private static void runSABigProblemComparison(int pStart, int pEnd) throws IOException {
    int n = 200;
    for (int p = pStart; p <= pEnd; p++) {
      PHub pHub = new PHub(n, p, Data.C, Data.T, Data.D,
          Paths.get("src", "main", "java", "es", "uma", "informatica", "misia", "ae", "simpleea", "data", "200-8.txt"));
      SimulatedAnnealing sa = new SimulatedAnnealing(pHub);

      long startTime = System.nanoTime();
      PHubSolution bestSolution = sa.run(Data.TEMP[1], Data.REDUCTION[1]);
      long elapsedTime = (System.nanoTime() - startTime) / 1_000_000;

      CsvWriter.writeResultsGAvsSA("SA", 6, n, p, bestSolution.getFitness(), elapsedTime);
    }
  }

  private static Map<String, Double> getParameters(int populationIndex, int probIndex) {
    Map<String, Double> parameters = new HashMap<>();
    parameters.put(EvolutionaryAlgorithm.POPULATION_SIZE_PARAM, Data.POPULATION_SIZES[populationIndex]);
    parameters.put(EvolutionaryAlgorithm.MAX_FUNCTION_EVALUATIONS_PARAM, 10_000.0);
    parameters.put(PHubMutation.BIT_FLIP_PROBABILITY_PARAM, Data.MUTATION_PROBS[probIndex]);
    parameters.put(EvolutionaryAlgorithm.RANDOM_SEED_PARAM, (double) System.currentTimeMillis());
    return parameters;
  }
}
