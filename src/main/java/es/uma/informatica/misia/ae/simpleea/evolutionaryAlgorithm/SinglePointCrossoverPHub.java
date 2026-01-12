package es.uma.informatica.misia.ae.simpleea.evolutionaryAlgorithm;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import es.uma.informatica.misia.ae.simpleea.common.*;

public class SinglePointCrossoverPHub implements Crossover {

  private Random rnd;

  private double[][] dist;

  public SinglePointCrossoverPHub(Random rnd, double[][] dist) {
    this.rnd = rnd;
    this.dist = dist;
  }

  @Override
  public PHubSolution apply(Individual individual1, Individual individual2) {
    PHubSolution parent1 = (PHubSolution) individual1;
    PHubSolution parent2 = (PHubSolution) individual2;
    int[] childHubs = this.crossHubs(parent1.hubs, parent2.hubs, parent1.allocations.length);
    int[] childAllocations = this.crossAllocations(parent1.allocations, parent2.allocations, childHubs);
    return new PHubSolution(childHubs, childAllocations);
  }

  private int[] crossHubs(int[] hubs1, int[] hubs2, int n) {
    int p = hubs1.length;
    int cutPoint = rnd.nextInt(1, p);
    Set<Integer> childHubsSet = new LinkedHashSet<>();
    for (int i = 0; i < cutPoint; i++) {
      childHubsSet.add(hubs1[i]);
    }
    for (int i = 0; i < hubs2.length && childHubsSet.size() < p; i++) {
      childHubsSet.add(hubs2[i]);
    }
    while (childHubsSet.size() < p) {
      int candidate = rnd.nextInt(n);
      childHubsSet.add(candidate);
    }

    int[] childHubs = new int[p];
    int idx = 0;
    for (int hub : childHubsSet) {
      childHubs[idx++] = hub;
    }
    return childHubs;
  }

  private int[] crossAllocations(int[] allocations1, int[] allocations2, int[] hubs) {
    int n = allocations1.length;
    int cutPoint = rnd.nextInt(1, n);
    int[] childAllocations = new int[n];
    for (int i = 0; i < cutPoint; i++) {
      childAllocations[i] = allocations1[i];
    }
    for (int i = cutPoint; i < allocations2.length; i++) {
      childAllocations[i] = allocations2[i];
    }

    correctAllocations(childAllocations, hubs, n);

    return childAllocations;
  }

  public void correctAllocations(int[] allocations, int[] hubs, int n) {
    for (int i = 0; i < n; i++) {
      if (Utils.contains(hubs, i)) {
        allocations[i] = i;
      } else if (!Utils.contains(hubs, allocations[i])) {
        allocations[i] = Utils.closestHub(hubs, dist, i);
      }
    }
  }
}
