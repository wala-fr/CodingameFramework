package fr.code.utils.search.way;

import fr.code.utils.Utils;
import fr.code.variable.Parameter;
import fr.framework.AssertUtils;
import fr.framework.FrameworkConstant;
import fr.framework.MapUtils;
import fr.framework.point.PointUtils;

public class WayUtils extends Utils {

  private static int index = 0;
  private static final int SCORE_INDEX = index;

  static {
    index += 8;
  }

  private static final int MAP_INDEX = index;
  private static final int BIT_NB = 8;

  static {
    index += Math.ceil(FrameworkConstant.CASE_NB / (double) BIT_NB);
  }

  private static final int WAY_LENGTH_INDEX = index++;
  private static final int WAY_POSITIONS_START_INDEX = index;

  static {
    index += Parameter.WAY_SAVE_LENGTH;
  }

  private static final int LENGTH = index;
  // usually there are more fields.
  // We could allow to go through the same position multiple times (for a real beam search)...

  public static byte[] constructWay() {
    return new byte[LENGTH];
  }

  public static void reset(byte[] way) {
    resetLength(way);
    setScore(way, 0);
  }

  public static byte[] calculateStartWay(byte[] map, int startPosition) {
    if (MapUtils.isFree(map, startPosition)) {
      byte[] ret = WayCache.getNext();
      reset(ret);
      addNextPosition(ret, startPosition);
      setScore(ret, calculateScore(ret));
      return ret;
    }
    return null;
  }

  public static byte[] calculateNextWay(byte[] way, int nextPosition) {
    if (isDonePosition(way, nextPosition)) {
      // if you don't allow going back
      return null;
    }
    byte[] ret = copy(way);
    addNextPosition(ret, nextPosition);
    setScore(ret, calculateScore(ret));
    return ret;
  }

  public static byte[] copy(byte[] way) {
    byte[] ret = WayCache.getNext();
    System.arraycopy(way, 0, ret, 0, WAY_LENGTH_INDEX + 1 + getLastPositionIndex(way));
    return ret;
  }

  private static void addNextPosition(byte[] way, int newPosition) {
    setPosition(way, newPosition, getLength(way));
    incrementLength(way);
  }

  public static void setPosition(byte[] way, int position, int round) {
    int index = getPositionIndex(way, round);
    setField(way, WAY_POSITIONS_START_INDEX + index, position);
    setDonePosition(way, position);
  }

  public static int getLength(byte[] way) {
    return getField(way, WAY_LENGTH_INDEX);
  }

  public static int getPosition(byte[] way, int round) {
    if (round < 0) {
      return FrameworkConstant.OUT;
    }
    int index = getPositionIndex(way, round);
    return getField(way, WAY_POSITIONS_START_INDEX + index);
  }

  private static int getLastPositionIndex(byte[] way) {
    return getPositionIndex(way, getLength(way) - 1);
  }

  private static int getPositionIndex(byte[] way, int round) {
    if (round >= Parameter.WAY_SAVE_LENGTH) {
      return Parameter.WAY_SAVE_LENGTH - 1;
    }
    return round;
  }

  public static int getLastPosition(byte[] way) {
    return getPosition(way, getLastPositionIndex(way));
  }

  private static void incrementLength(byte[] way) {
    way[WAY_LENGTH_INDEX]++;
  }

  private static void resetLength(byte[] way) {
    setField(way, WAY_LENGTH_INDEX, 0);
  }

  public static void setDonePosition(byte[] way, int position) {
    way[MAP_INDEX + position / BIT_NB] |= 1 << (position % BIT_NB);
  }

  public static boolean isDonePosition(byte[] way, int position) {
    return (way[MAP_INDEX + position / BIT_NB] & (1 << (position % BIT_NB))) != 0;
  }

  public static void setScore(byte[] way, double dblValue) {
    long l = Double.doubleToLongBits(dblValue);
    for (int i = 7; i >= 0; i--) {
      setField(way, SCORE_INDEX + i, (byte) l & 0xFF);
      l >>= 8;
    }
    double tmp = getScore(way);
    AssertUtils.test(dblValue == tmp, tmp, dblValue);
  }

  public static double getScore(byte[] way) {
    long result = 0;
    for (int i = 0; i < 8; i++) {
      result <<= 8;
      result |= (getField(way, SCORE_INDEX + i) & 0xFF);
    }
    return Double.longBitsToDouble(result);
  }

  private static int getField(byte[] way, int index) {
    return way[index];
  }

  private static void setField(byte[] way, int index, int value) {
    way[index] = (byte) value;
  }

  public static double calculateScore(byte[] way) {
    // heuristic...(usually in another class)
    return 0;
  }

  public static boolean contains(byte[] way, int p) {
    for (int i = 0; i < getLength(way); i++) {
      if (p == getPosition(way, i)) {
        return true;
      }
    }
    return false;
  }

  public static String toString(byte[] way) {
    if (way == null) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    sb.append('[');
    sb.append(getLength(way));
    sb.append(',').append(' ');
    for (int i = 0; i <= getLastPositionIndex(way); i++) {
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

  public static String toMapString(byte[] way) {
    if (way == null) {
      return null;
    }
    byte[] map = MapUtils.createNewMap();
    for (int p = 0; p < map.length; p++) {
      map[p] = (byte) (isDonePosition(way, p) ? FrameworkConstant.WALL : FrameworkConstant.FREE);
    }
    return MapUtils.toString(map);
  }
}
