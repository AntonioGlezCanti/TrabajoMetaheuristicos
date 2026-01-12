package es.uma.informatica.misia.ae.simpleea.evolutionaryAlgorithm;

import java.util.Random;
import es.uma.informatica.misia.ae.simpleea.common.*;

public class PHubMutation implements Mutation {

  public static final String BIT_FLIP_PROBABILITY_PARAM = "bitFlipProbability";

  private double allocationSwapProb;
  private Random rnd;

  public PHubMutation(Double allocationSwapProb, Random rnd) {
    this.allocationSwapProb = allocationSwapProb;
    this.rnd = rnd;
  }

  @Override
  public Individual apply(Individual individual) {
    PHubSolution original = (PHubSolution) individual;
    PHubSolution mutated = new PHubSolution(original);
    if (this.rnd.nextDouble() < this.allocationSwapProb) {
      this.mutateAllocations(mutated.hubs, mutated.allocations);
    }

    return mutated;
  }

  public void mutateAllocations(int[] hubs, int[] allocations) {
    int n = allocations.length;
    int p = hubs.length;
    this.swapBetweenTwoAllocations(n, hubs, allocations);
    Utils.swapNodeAllocations(n, p, hubs, allocations, this.rnd);
  }

  private void swapBetweenTwoAllocations(int n, int[] hubs, int[] allocations) {
    int i, j;
    do {
      i = this.rnd.nextInt(n);
    } while (Utils.contains(hubs, i));

    do {
      j = this.rnd.nextInt(n);
    } while (i == j || Utils.contains(hubs, j));

    int iValue = allocations[i];
    allocations[i] = allocations[j];
    allocations[j] = iValue;
  }
}
