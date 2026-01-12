package es.uma.informatica.misia.ae.simpleea.common;

import java.util.Random;

public class Utils {

  public static boolean contains(int[] list, int value) {
    for (int i = 0; i < list.length; i++) {
      if (list[i] == value) {
        return true;
      }
    }
    return false;
  }

  public static int closestHub(int[] hubs, double[][] dist, int node) {
    int bestHub = hubs[0];
    double bestWeight = dist[node][bestHub];

    for (int j = 1; j < hubs.length; j++) {
      double currentWeight = dist[node][hubs[j]];
      if (currentWeight < bestWeight) {
        bestWeight = currentWeight;
        bestHub = hubs[j];
      }
    }
    return bestHub;
  }

  public static void swapNodeAllocations(int n, int p, int[] hubs, int[] allocations, Random rnd) {
    int i;
    do {
      i = rnd.nextInt(n);
    } while (Utils.contains(hubs, i));

    int newHub;
    do {
      newHub = hubs[rnd.nextInt(p)];
    } while (newHub == allocations[i]);
    allocations[i] = newHub;
  }
}
