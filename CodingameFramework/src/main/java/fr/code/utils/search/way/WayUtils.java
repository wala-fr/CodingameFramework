package fr.code.utils.search.way;

import fr.code.utils.Utils;
import fr.code.variable.Parameter;
import fr.framework.MapUtils;
import fr.framework.point.PointUtils;

public class WayUtils extends Utils {

  private static int index = 0;
  private static final int SCORE_INDEX = index++;
  private static final int WAY_LENGTH_INDEX = index++;
  private static final int WAY_POSITIONS_START_INDEX = index;
  // usually there are more fields. We could avoid storing the whole way (only the first position
  // and bitboards for done positions) or even allow to go through the same position multiple times
  // (for a real beam search)...

  static {
    index += Parameter.WAY_MAX_LENGTH;
  }

  private static final int LENGTH = index;

  public static byte[] constructWay() {
    return new byte[LENGTH];
  }

  public static void reset(byte[] way) {
    resetLength(way);
    setScore(way, 0);
  }

  public static byte[] calculateStartWay(byte[] map, byte startPosition) {
    if (MapUtils.isFree(map, startPosition)) {
      byte[] ret = WayCache.getNext();
      reset(ret);
      addNextPosition(ret, startPosition);
      setScore(ret, calculateScore(ret));
      return ret;
    }
    return null;
  }

  public static byte[] calculateNextWay(byte nextPosition, byte[] way) {
    if (contains(way, nextPosition)) {
      return null;
    }
    byte[] ret = copy(way);
    addNextPosition(ret, nextPosition);
    setScore(ret, calculateScore(ret));
    return ret;
  }

  public static byte[] copy(byte[] way) {
    byte[] ret = WayCache.getNext();
    System.arraycopy(way, 0, ret, 0, WAY_LENGTH_INDEX + 1 + getLength(way));
    return ret;
  }

  private static void addNextPosition(byte[] way, byte newPosition) {
    setPosition(way, newPosition, getLength(way));
    incrementLength(way);
  }

  public static void setPosition(byte[] way, byte position, int round) {
    way[WAY_POSITIONS_START_INDEX + round] = position;
  }

  public static byte getLength(byte[] way) {
    return way[WAY_LENGTH_INDEX];
  }

  public static byte getPosition(byte[] way, int round) {
    return way[WAY_POSITIONS_START_INDEX + round];
  }

  public static byte getLastPosition(byte[] way) {
    return getPosition(way, getLength(way) - 1);
  }

  private static void incrementLength(byte[] way) {
    way[WAY_LENGTH_INDEX]++;
  }

  private static void resetLength(byte[] way) {
    way[WAY_LENGTH_INDEX] = 0;
  }

  public static byte getScore(byte[] way) {
    return way[SCORE_INDEX];
  }

  public static void setScore(byte[] way, int score) {
    way[SCORE_INDEX] = (byte) score;
  }

  public static byte calculateScore(byte[] way) {
    // heuristic...(usually in another class)
    return 0;
  }

  public static boolean contains(byte[] way, byte p) {
    for (int i = 0; i < getLength(way); i++) {
      if (p == getPosition(way, i)) {
        return true;
      }
    }
    return false;
  }

  public static String toString(byte[] way) {
    StringBuilder sb = new StringBuilder();
    sb.append('[');
    sb.append(getLength(way));
    sb.append(',').append(' ');
    for (int i = 0; i < getLength(way); i++) {
      if (i > 0) {
        sb.append(',').append(' ');
      }
      sb.append(PointUtils.toPoint(getPosition(way, i)));
    }
    sb.append(']');
    sb.append(", score = ");
    sb.append(getScore(way));
    return sb.toString();
  }
}
