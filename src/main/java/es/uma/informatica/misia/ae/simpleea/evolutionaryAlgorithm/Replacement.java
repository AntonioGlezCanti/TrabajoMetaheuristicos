package es.uma.informatica.misia.ae.simpleea.evolutionaryAlgorithm;

import java.util.List;

public interface Replacement {

  List<Individual> replacement(List<Individual> population, List<Individual> offspring);

}
