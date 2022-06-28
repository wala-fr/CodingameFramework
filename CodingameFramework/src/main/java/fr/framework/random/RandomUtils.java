package fr.framework.random;

import java.util.Random;

public class RandomUtils {

  // can switch to new Random() also
  private static Random random = new XORShiftRandom();

  /** return random int i : 0 <= i < nb */
  public static int chooseRandom(int nb) {
    return random.nextInt(nb);
  }
}
