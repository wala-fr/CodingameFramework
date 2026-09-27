package fr.framework.random;

import java.util.SplittableRandom;

public class RandomUtils {

  private static final SplittableRandom random = new SplittableRandom();

  /** return random int i : 0 <= i < nb */
  public static int chooseRandom(int nb) {
    return random.nextInt(nb);
  }
}
