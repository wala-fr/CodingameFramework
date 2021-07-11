package code.utils.search;

import code.utils.Utils;
import code.variable.Parameter;
import fr.framework.MapUtils;
import fr.framework.logger.Logger;
import fr.framework.timeout.TimeoutException;
import fr.framework.timeout.TimeoutUtils;

public class BeamWaySearcher extends Utils {

  private static Logger logger = Logger.getLogger(BeamWaySearcher.class);
  
  private static BeamWaySearcher instance = new BeamWaySearcher();
  private int indexStartTimeout;
  private int timeout;

  private BeamWaySearcher() {}

  public static BeamWaySearcher getInstance() {
    return instance;
  }

  public byte[] findWay(byte startPosition, byte endPosition, byte[] map) {
    WayCache.reset();
    initTimeOut();
    HeapStock ways = HeapStock.get(0);

    byte[] nextWay = WayUtils.calculateStartWay(map, startPosition);
    if (nextWay == null) {
      // no way (in a wall...)
      return null;
    }
    ways.add(nextWay, WayUtils.getScore(nextWay));
    int count = 0;
    HeapStock tmpWays = HeapStock.get(1 - ways.getCacheIndex());
    int simNb = 0;
    try {
      while (!ways.isEmpty()) {
        count++;
        if (count >= Parameter.WAY_MAX_LENGTH) {
          logger.error("TOO LONG");
          break;
        }
        logger.error("count", count, ways.size());
        tmpWays = HeapStock.get(1 - ways.getCacheIndex());
        int length = ways.size();
        for (int j = 0; j < length; j++) {
          if (count >= indexStartTimeout) {
            // to avoid getting time on the first loops when it's sure there"s no timeout
            TimeoutUtils.stopTimeException(timeout);
          }
          byte[] way = ways.getObject(j);

          byte[] nextPositions = MapUtils.getAroundPositions(WayUtils.getLastPosition(way));

          for (byte i = 0; i < nextPositions.length; i++) {
            simNb++;
            byte nextPosition = nextPositions[i];
            nextWay = WayUtils.calculateNextWay(nextPosition, way);
            if (nextWay != null) {
              // only for the BFS JUnit test
              if (WayUtils.getLastPosition(nextWay) == endPosition) {
                return nextWay;
              }
              tmpWays.add(nextWay, WayUtils.getScore(nextWay));
            }
          }
        }
        ways = tmpWays;
      }
    } catch (NoMoreWayException e) {
      logger.error("NO MORE WAY IN CACHE");
    } catch (TimeoutException e) {
      logger.error("TIME OUT", timeout);
    }
    logger.error("END count", count, "ways", ways.size(), "simNb", simNb);
    return chooseBestWay(ways);
  }

  private void initTimeOut() {
    timeout = game.isFirstRound() ? Parameter.TIMEOUT_FIRST_ROUND : Parameter.TIMEOUT;
    indexStartTimeout = Parameter.TIMEOUT_START_INDEX;
  }

  private byte[] chooseBestWay(HeapStock ways) {
    int length = ways.size();
    double maxScore = Double.NEGATIVE_INFINITY;
    byte[] bestWay = null;
    for (int j = 0; j < length; j++) {
      double score = ways.getScore(j);
      if (score > maxScore) {
        bestWay = ways.getObject(j);
        maxScore = score;
      }
    }
    return bestWay;
  }
}
