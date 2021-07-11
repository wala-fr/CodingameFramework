package fr.framework.timeout;

import code.variable.Parameter;
import fr.framework.logger.Logger;
import fr.framework.logger.Timer;

public class GarbageCollectorUtils {

  private static Logger logger = Logger.getLogger(GarbageCollectorUtils.class);

  private static long start;

  public static void avoidTimeOut() {
    StringBuilder[] tmp = new StringBuilder[Parameter.AVOID_TIMEOUT_NB];
    for (int i = 0; i < Parameter.AVOID_TIMEOUT_NB; i++) {
      tmp[i] = new StringBuilder();
    }
    tmp = null;
    garbageCollect();
  }

  public static void garbageCollect() {
    // no logger because in some multis like UTTT i removed the Logger class to try improving the
    // performances
    System.err.println(
        (Timer.getInstance().getTime())
            + " GARBAGE COLLECTOR START "
            + Runtime.getRuntime().freeMemory());
    System.gc();
    System.err.println(
        (Timer.getInstance().getTime())
            + " GARBAGE COLLECTOR END "
            + Runtime.getRuntime().freeMemory());
  }

  public static void initFreeMemory() {
    start = Runtime.getRuntime().freeMemory();
  }

  /**
   * to check if garbage collectable objects are created during each round
   */
  public static void printUsedMemory() {
    logger.error("GARBAGE COLECTOR", Runtime.getRuntime().freeMemory() - start);
  }
}
