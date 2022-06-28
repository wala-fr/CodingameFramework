package fr.code.utils.search.way;

import fr.code.variable.Parameter;
import fr.framework.logger.Logger;

public class WayCache {

  private static final Logger logger = Logger.getLogger(WayCache.class);

  private static final byte[][] CACHE = new byte[Parameter.WAY_CACHE_NB][];
  private static int maxIndex;
  private static int index;

  public static void reset() {
    maxIndex = Math.max(maxIndex, index);
    index = 0;
  }

  public static byte[] getNext() throws NoMoreWayException {
    try {
      byte[] ret = CACHE[index];
      if (ret == null) {
        ret = WayUtils.constructWay();
        CACHE[index] = ret;
      }
      index++;
      return ret;
    } catch (IndexOutOfBoundsException e) {
      throw NoMoreWayException.EXCEPTION;
    }
  }

  /** to see the max number of used ways, to adjust the cache size */
  public static void printIndex() {
    logger.error("WayCacheIndex=", index, "maxIndex=", maxIndex);
  }
}
