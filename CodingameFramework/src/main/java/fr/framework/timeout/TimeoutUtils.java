package fr.framework.timeout;

import fr.framework.logger.Timer;

public class TimeoutUtils {

  private static Timer timer = Timer.getInstance();

  public static void stopTimeException(int timeout) throws TimeoutException {
    if (isStopTime(timeout)) {
      throw TimeoutException.EXCEPTION;
    }
  }

  public static boolean isStopTime(int timeout) {
    return timer.getTime() >= timeout;
  }
}
