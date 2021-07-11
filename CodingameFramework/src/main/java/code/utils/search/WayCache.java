package code.utils.search;

import code.variable.Parameter;
import fr.framework.logger.Logger;

public class WayCache {

  private static Logger logger = Logger.getLogger(WayCache.class);

  private static byte[][] CACHE = new byte[Parameter.WAY_CACHE_NB][];
  private static int maxIndex;
  private static int index;

  public static void reset() {
    index = 0;
  }

  public static byte[] getWay() throws NoMoreWayException {
    try {
      byte[] ret = CACHE[index];
      if (ret == null) {
        ret = WayUtils.constructWay();
        CACHE[index] = ret;
      }
      index++;
      return ret;
    } catch (IndexOutOfBoundsException e) {
      throw new NoMoreWayException();
    }
  }

  public static void printIndex() {
    maxIndex = Math.max(maxIndex, index);
    logger.error("WayCacheIndex=", index, "maxIndex=", maxIndex);
  }
}
