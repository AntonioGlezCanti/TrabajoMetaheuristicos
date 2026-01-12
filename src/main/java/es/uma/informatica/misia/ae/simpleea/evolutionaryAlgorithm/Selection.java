package es.uma.informatica.misia.ae.simpleea.evolutionaryAlgorithm;

import java.util.List;

public interface Selection {
  Individual selectParent(List<Individual> population);
}
