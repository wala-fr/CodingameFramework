package fr.code.utils.search;

import fr.code.utils.Utils;
import fr.code.utils.search.heap.Heap;
import fr.code.utils.search.heap.HeapCache;
import fr.code.utils.search.way.NoMoreWayException;
import fr.code.utils.search.way.WayCache;
import fr.code.utils.search.way.WayUtils;
import fr.code.variable.Parameter;
import fr.framework.MapUtils;
import fr.framework.logger.Logger;
import fr.framework.timeout.TimeoutException;
import fr.framework.timeout.TimeoutUtils;

public class WayBeamSearcher extends Utils {

  private static final Logger logger = Logger.getLogger(WayBeamSearcher.class);

  private static WayBeamSearcher instance = new WayBeamSearcher();
  private int indexStartTimeout;
  private int timeout;

  private WayBeamSearcher() {}

  public static WayBeamSearcher getInstance() {
    return instance;
  }

  /**
   * For a real beam search there wouldn't be an end position. It's only for testing purposes. As it
   * stands it's more a breadth-first search. In the tests the heap will never be full.
   */
  public byte[] findWay(byte startPosition, byte endPosition, byte[] map) {

    WayCache.reset();
    initTimeOut();
    Heap currentWays = HeapCache.get(0);
    currentWays.clear();

    byte[] startWay = WayUtils.calculateStartWay(map, startPosition);
    if (startWay == null) {
      // no way (in a wall...)
      return null;
    }
    currentWays.insert(startWay, WayUtils.getScore(startWay));
    int count = 0;
    int simNb = 0;
    try {
      while (true) {
        count++;
        if (count >= Parameter.WAY_MAX_LENGTH) {
          logger.error("TOO LONG", count);
          break;
        }
        logger.error("count =", count, ", heap size =", currentWays.size());
        Heap nextWays = HeapCache.get(1 - currentWays.getIndex());
        nextWays.clear();

        int length = currentWays.size();
        for (int j = 0; j < length; j++) {
          if (count >= indexStartTimeout) {
            // to avoid looking up current time on the first loop iterations when it's sure that
            // there will
            // be no timeout
            TimeoutUtils.stopTimeException(timeout);
          }
          byte[] way = currentWays.get(j);

          byte[] nextPositions = MapUtils.getAroundPositions(WayUtils.getLastPosition(way));

          for (byte i = 0; i < nextPositions.length; i++) {
            simNb++;
            byte nextPosition = nextPositions[i];
            byte[] nextWay = WayUtils.calculateNextWay(nextPosition, way);
            if (nextWay != null) {
              // only for the BFS JUnit test
              if (WayUtils.getLastPosition(nextWay) == endPosition) {
                return nextWay;
              }
              nextWays.insert(nextWay, WayUtils.getScore(nextWay));
            }
          }
        }
        //  uncomment to handle properly the end game of a real beam search
        //        if (nextWays.isEmpty()) {
        //          logger.error("OVER");
        //          break;
        //        }
        currentWays = nextWays;
      }
    } catch (NoMoreWayException e) {
      logger.error("NO MORE WAY IN CACHE");
    } catch (TimeoutException e) {
      logger.error("TIME OUT", timeout);
    }
    logger.error("END count = ", count, ", ways =", currentWays.size(), ", simNb =", simNb);
    return chooseBestWay(currentWays);
  }

  private void initTimeOut() {
    timeout = game.isFirstRound() ? Parameter.TIMEOUT_FIRST_ROUND : Parameter.TIMEOUT;
    indexStartTimeout = Parameter.TIMEOUT_START_INDEX;
  }

  private byte[] chooseBestWay(Heap ways) {
    int length = ways.size();
    double maxScore = Double.NEGATIVE_INFINITY;
    byte[] bestWay = null;
    for (int j = 0; j < length; j++) {
      double score = ways.getValue(j);
      if (score > maxScore) {
        bestWay = ways.get(j);
        maxScore = score;
      }
    }
    return bestWay;
  }
}
