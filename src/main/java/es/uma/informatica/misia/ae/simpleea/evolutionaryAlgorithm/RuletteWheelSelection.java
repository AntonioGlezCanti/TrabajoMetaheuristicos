package es.uma.informatica.misia.ae.simpleea.evolutionaryAlgorithm;

import java.util.List;
import java.util.Random;

public class RuletteWheelSelection implements Selection {
  private Random rnd;

  public RuletteWheelSelection(Random rnd) {
    this.rnd = rnd;
  }

  private List<Double> getInversedProbabilities(List<Individual> population) {
    return population.stream()
        .map(individual -> 1 / individual.getFitness())
        .toList();
  }

  @Override
  public Individual selectParent(List<Individual> population) {
    int parentPosition;
    double prob;
    List<Double> inversedProbabilities = this.getInversedProbabilities(population);
    double invertedSum = inversedProbabilities.stream().mapToDouble(f -> f).sum();
    do {
      parentPosition = this.rnd.nextInt(population.size());
      prob = inversedProbabilities.get(parentPosition) / invertedSum;
    } while (this.rnd.nextDouble() >= prob);

    return population.get(parentPosition);
  }

}
