package es.uma.informatica.misia.ae.simpleea.common;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import es.uma.informatica.misia.ae.simpleea.evolutionaryAlgorithm.*;

public class PHubSolution extends Individual {

  public int[] hubs;
  public int[] allocations;
  private double[][] distances;

  public PHubSolution(int[] hubs, int[] allocations) {
    this.hubs = Arrays.copyOf(hubs, hubs.length);
    this.allocations = Arrays.copyOf(allocations, allocations.length);
  }

  public PHubSolution(int n, int p, double[][] dist, Random rnd) {
    this.distances = dist;
    this.setRandomHubs(n, p, rnd);
    this.allocateNodeToClosestHub(n, p, rnd);
  }

  public PHubSolution(PHubSolution individual) {
    this(individual.hubs, individual.allocations);
    this.fitness = individual.fitness;
  }

  private void setRandomHubs(int n, int p, Random rnd) {
    List<Integer> ints = IntStream.rangeClosed(0, n - 1)
        .boxed()
        .collect(Collectors.toList());
    Collections.shuffle(ints);
    this.hubs = new int[p];
    for (int i = 0; i < p; i++) {
      this.hubs[i] = ints.get(i);
    }
  }

  private void allocateNodeToClosestHub(int n, int p, Random rnd) {
    this.allocations = new int[n];
    for (int i = 0; i < n; i++) {
      if (Utils.contains(this.hubs, i)) {
        this.allocations[i] = i;
      } else {
        this.allocations[i] = Utils.closestHub(hubs, this.distances, i);
      }
    }
  }

  @Override
  public String toString() {
    return "Solution: {hubs: " + Arrays.toString(this.hubs) + ", allocations: " + Arrays.toString(this.allocations)
        + ", fitness: " + this.fitness + "}";
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof PHubSolution)) {
      return false;
    }
    PHubSolution solution = (PHubSolution) object;
    return this.hubs.equals(solution.hubs) && this.allocations.equals(solution.allocations);
  }
}
