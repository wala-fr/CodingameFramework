package fr.code.utils.search.way;

import fr.code.variable.Parameter;
import fr.framework.AssertUtils;
import fr.framework.logger.Logger;

/**
 * in case of Beam search a "queue cache" can be used, it allows to never run out of way since the
 * number of used ways will never exceeds twice the beam search width
 */
public class WayQueueCache {

  private static final Logger logger = Logger.getLogger(WayQueueCache.class);

  private static final byte[][] CACHE = new byte[Parameter.WAY_QUEUE_CACHE_NB][];
  private static final byte[][] QUEUE = new byte[Parameter.WAY_QUEUE_CACHE_NB][];

  public static int indexPull;
  public static int indexPut;

  static {
    for (int i = 0; i < CACHE.length; i++) {
      CACHE[i] = WayUtils.constructWay();
    }
  }

  public static void reset() {
    indexPull = 0;
    indexPut = 0;
    System.arraycopy(CACHE, 0, QUEUE, 0, QUEUE.length);
  }

  public static byte[] next() {
    byte[] ret = QUEUE[indexPull];
//     to debug
//     logger.error("indexPull", indexPull);
//     int d = indexPull > indexPut ? indexPull - indexPut : (indexPull - indexPut + QUEUE.length);
//     AssertUtils.test(d > 0);

    AssertUtils.test(ret != null, indexPull);
    QUEUE[indexPull] = null;
    indexPull++;
    if (indexPull == QUEUE.length) {
      indexPull = 0;
    }
    return ret;
  }

  public static void put(byte[] ret) {
    // logger.error("indexPut", indexPut);
    QUEUE[indexPut++] = ret;
    if (indexPut == QUEUE.length) {
      indexPut = 0;
    }
  }

}
