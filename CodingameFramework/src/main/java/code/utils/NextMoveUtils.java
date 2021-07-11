package code.utils;

import code.utils.search.FindBeamWayCalculator;
import code.utils.search.WayCache;
import code.utils.search.WayUtils;
import fr.framework.MapUtils;
import fr.framework.PointUtils;
import fr.framework.logger.Logger;
import fr.framework.timeout.GarbageCollectorUtils;

public class NextMoveUtils extends Utils {

  private static Logger logger = Logger.getLogger(NextMoveUtils.class);

  public static void proceed() {}

  /**
   * for demo purpose usually the code would be in the proceed() method
   * @return 
   */
  public static byte[] proceed(byte startPosition, byte endPosition, byte[] map) {
    GarbageCollectorUtils.initFreeMemory();
    FirstRoundUtils.proceed(map);

    // for testing purpose the search is not a beam search but a BFS (the heap is never full nad all
    // ways have the same score)
    byte[] way =
        FindBeamWayCalculator.getInstance().findWay(startPosition, endPosition, map);
    if (way == null) {
      logger.error(
          "NO WAY FROM",
          PointUtils.toPoint(startPosition),
          "TO",
          PointUtils.toPoint(endPosition));
    } else {
      logger.error(WayUtils.toString(way));
      byte[] tmpMap = MapUtils.copy(map);
      for (int i = 0; i < WayUtils.getLength(way); i++) {
        tmpMap[WayUtils.getPosition(way, i)] = 2;
      }
      logger.error(MapUtils.toString(tmpMap));
    }
    WayCache.printIndex();
    GarbageCollectorUtils.printUsedMemory();
    return way;
  }
}
