package es.uma.informatica.misia.ae.simpleea.simulatedAnnealing;

import java.util.Random;

import es.uma.informatica.misia.ae.simpleea.common.*;

public class SimulatedAnnealing {

  private Random rnd;
  private int p;
  private int n;
  private PHub pHub;

  public SimulatedAnnealing(PHub pHub) {
    this.n = pHub.n;
    this.p = pHub.p;
    this.pHub = pHub;
    this.rnd = new Random();
  }

  public PHubSolution run(int initialTemp, double reduction) {

    Random rnd = new Random();

    int maxIterations = 10000;
    double temp = initialTemp;
    int l = maxIterations / initialTemp;
    int iterations = 0;

    PHubSolution solution = this.pHub.generateRandomIndividual(rnd);
    this.evaluateIndividual(this.pHub, solution);

    while (iterations < maxIterations) {
      for (int i = 0; i < l; i++) {
        PHubSolution neighbour = selectNeighbour(solution);
        this.evaluateIndividual(this.pHub, neighbour);
        double dif = neighbour.getFitness() - solution.getFitness();
        if (dif < 0 || this.shouldAcceptSolution(temp, dif, rnd)) {
          solution = neighbour;
        }
        iterations++;
      }
      temp *= reduction;
    }

    return solution;
  }

  private PHubSolution selectNeighbour(PHubSolution solution) {
    PHubSolution neighbour = new PHubSolution(solution);
    if (this.rnd.nextDouble() < 0.4) {
      Utils.swapNodeAllocations(this.n, this.p, neighbour.hubs, neighbour.allocations, this.rnd);
    } else {
      this.move(neighbour);
    }

    return neighbour;
  }

  private void move(PHubSolution solution) {
    int[] allocations = solution.allocations;
    int[] hubs = solution.hubs;
    int selectedHubPos = this.rnd.nextInt(this.p);
    int selectedHub = hubs[selectedHubPos];
    if (this.isHubSingleton(allocations, selectedHub)) {
      this.rebuildSingleton(allocations, solution.hubs, selectedHub, selectedHubPos);
      return;
    }

    int selectedNode;
    do {
      selectedNode = this.rnd.nextInt(this.n);
    } while (allocations[selectedNode] != selectedHub);

    for (int i = 0; i < n; i++) {
      if (allocations[i] == selectedHub) {
        allocations[i] = selectedNode;
      }
    }
    hubs[selectedHubPos] = selectedNode;
  }

  private boolean isHubSingleton(int[] allocations, int hub) {
    for (int i = 0; i < this.n; i++) {
      if (i != hub && allocations[i] == hub) {
        return false;
      }
    }
    return true;
  }

  private void rebuildSingleton(int[] allocations, int[] hubs, int hub, int hubPos) {
    int newHub;
    do {
      newHub = rnd.nextInt(n);
    } while (Utils.contains(hubs, newHub));
    hubs[hubPos] = newHub;
    allocations[newHub] = newHub;
    allocations[hub] = newHub;
  }

  private boolean shouldAcceptSolution(double temp, double dif, Random rnd) {
    return rnd.nextDouble() < Math.exp((-dif / temp));
  }

  private void evaluateIndividual(PHub pHub, PHubSolution solution) {
    double fitness = pHub.evaluate(solution);
    solution.setFitness(fitness);
  }
}
