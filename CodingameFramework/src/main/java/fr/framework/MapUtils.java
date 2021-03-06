package fr.framework;

import java.util.List;
import fr.framework.logger.Logger;
import fr.framework.point.Direction;
import fr.framework.point.DirectionUtils;
import fr.framework.point.PointUtils;

public class MapUtils {

  private static final Logger logger = Logger.getLogger(MapUtils.class);

  private static byte[][] aroundPositionCache;

  public static boolean isFree(byte[] map, int p) {
    return map[p] == FrameworkConstant.FREE;
  }

  public static byte[] copy(byte[] map) {
    return ByteUtils.copy(map);
  }

  public static void copy(byte[] source, byte[] destination) {
    ByteUtils.copy(source, destination);
  }

  public static byte[] createNewMap() {
    return new byte[FrameworkConstant.CASE_NB];
  }

  public static int getNbAroundPositions(int p) {
    return getAroundPositions(p).length;
  }

  public static byte[] getAroundPositions(int p) {
    return aroundPositionCache[p];
  }

  public static void initCache(byte[] map) {
    logger.error("INIT CACHE START");
    aroundPositionCache = new byte[FrameworkConstant.CASE_NB][];
    for (int p = 0; p < FrameworkConstant.CASE_NB; p++) {
      if (isFree(map, p)) {
        calculateAroundPositions(p, map);
      }
    }
    logger.error("INIT CACHE END");
  }

  private static void calculateAroundPositions(int p, byte[] map) {
    byte[] ret = new byte[4];
    Direction[] dirs = Direction.values;
    int index = 0;
    for (int i = 0; i < dirs.length; i++) {
      Direction direction = dirs[i];
      int p2 = DirectionUtils.construct(p, direction);
      if (PointUtils.isIn(p2) && isFree(map, p2)) {
        ret[index++] = (byte) p2;
      }
    }
    aroundPositionCache[p] = ByteUtils.copy(ret, index);
  }

  public static String toString(byte[] map) {
    StringBuilder str = new StringBuilder();
    for (int p = 0; p < FrameworkConstant.CASE_NB; p++) {
      if (p % FrameworkConstant.WIDTH == 0) {
        str.append('\n');
      }
      int v = map[p];
      char c;
      if (v == FrameworkConstant.WALL) {
        c = '#';
      } else if (v == FrameworkConstant.FREE) {
        c = '.';
      } else {
        if (v >= 10) v = 9;
        c = Character.forDigit(v, 10);
      }
      str.append(c);
    }
    return str.toString();
  }

  public static byte[] extract(List<String> lines) {
    byte[] map = createNewMap();
    int lineNum = 0;
    for (String line : lines) {
      for (int x = 0; x < line.length(); x++) {
        char c = line.charAt(x);
        fromInput(c, lineNum, map);
        lineNum++;
      }
    }
    return map;
  }

  public static byte[] extract(String line, int lineNum, byte[] map) {
    int i = lineNum * FrameworkConstant.WIDTH;
    for (int x = 0; x < FrameworkConstant.WIDTH; x++) {
      fromInput(line.charAt(x), i + x, map);
    }
    return map;
  }

  public static void fromInput(char c, int p, byte[] map) {
    int v;
    if (c == '#') {
      v = FrameworkConstant.WALL;
    } else if (c == '.') {
      v = FrameworkConstant.FREE;
    } else {
      throw new IllegalStateException("WRONG CHAR *" + c + "* " + p);
    }
    map[p] = (byte) v;
  }
}
