package code.utils;

import fr.framework.MapUtils;
import fr.framework.logger.Logger;
import fr.framework.timeout.GarbageCollectorUtils;

public class FirstRoundUtils extends Utils {

  private static Logger logger = Logger.getLogger(FirstRoundUtils.class);

  public static void proceed(byte[] map) {
    if (game.isFirstRound()) {
      // init all caches 'around positions', 'distances'...
      MapUtils.initCache(map);
      GarbageCollectorUtils.avoidTimeOut();
    }
  } 
}
