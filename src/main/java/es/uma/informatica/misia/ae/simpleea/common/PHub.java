package es.uma.informatica.misia.ae.simpleea.common;

import java.util.Random;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.nio.file.Path;

import es.uma.informatica.misia.ae.simpleea.evolutionaryAlgorithm.*;

public class PHub implements Problem {

  public static final String N = "n";
  public static final String P = "p";
  public static final String POPULATION_SIZE_PARAM = "populationSize";

  public int n;
  public int p;
  public double c;
  public double t;
  public double d;
  public double[][] distances;
  public double[][] weights;

  public PHub(int n, int p, double c, double t, double d, Path path) {
    this.n = n;
    this.p = p;
    this.c = c;
    this.t = t;
    this.d = d;
    this.readFile(path);
  }

  public double evaluate(Individual individual) {
    PHubSolution solution = (PHubSolution) individual;
    double fitness = 0.0;
    int[] allocations = solution.allocations;
    for (int i = 0; i < allocations.length; i++) {
      for (Integer j = 0; j < allocations.length; j++) {
        int iHub = allocations[i];
        int jHub = allocations[j];
        double collectionCost = this.c * this.distances[i][iHub];
        double transferCost = this.t * this.distances[iHub][jHub];
        double distibutionCost = this.d * this.distances[jHub][j];
        fitness += this.weights[i][j] * (collectionCost + transferCost + distibutionCost);
      }
    }
    return fitness;
  }

  public PHubSolution generateRandomIndividual(Random rnd) {
    return new PHubSolution(n, p, this.distances, rnd);
  }

  public void readFile(Path path) {
    double[][] cords = new double[this.n][2];
    this.weights = new double[this.n][this.n];

    try (Scanner sc = new Scanner(path.toFile())) {

      for (int i = 0; i < this.n; i++) {
        cords[i][0] = sc.nextDouble();
        cords[i][1] = sc.nextDouble();
      }

      for (int i = 0; i < this.n; i++) {
        for (int j = 0; j < this.n; j++) {
          double value = sc.nextDouble();
          this.weights[i][j] = value;
        }
      }

      this.convertCordsToDistance(cords);

    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private void convertCordsToDistance(double[][] cords) {
    this.distances = new double[this.n][this.n];
    for (int i = 0; i < cords.length; i++) {
      for (int j = i + 1; j < cords.length; j++) {
        double x = cords[i][0] - cords[j][0];
        double y = cords[i][1] - cords[j][1];
        double distances = Math.sqrt(x * x + y * y);
        this.distances[i][j] = distances;
        this.distances[j][i] = distances;
      }
    }
  }
}
