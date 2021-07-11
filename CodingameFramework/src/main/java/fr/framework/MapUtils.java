package fr.framework;

import java.util.List;
import fr.framework.logger.Logger;

public class MapUtils {

  private static Logger logger = Logger.getLogger(MapUtils.class);

  private static byte[][] aroundPositionCache;

  public static boolean isFree(byte[] map, byte p) {
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

  public static int getNbAroundPositions(byte p) {
    return getAroundPositions(p).length;
  }

  public static byte[] getAroundPositions(byte p) {
    return aroundPositionCache[p];
  }

  public static void initCache(byte[] map) {
    logger.error("INIT CACHE START");
    aroundPositionCache = new byte[FrameworkConstant.CASE_NB][];
    for (byte p = 0; p < FrameworkConstant.CASE_NB; p++) {
      if (isFree(map, p)) {
        calculateAroundPositions(p, map);
      }
    }
    logger.error("INIT CACHE END");
  }

  private static void calculateAroundPositions(byte p, byte[] map) {
    byte[] ret = new byte[4];
    Direction[] dirs = Direction.values();
    int index = 0;
    for (int i = 0; i < dirs.length; i++) {
      Direction direction = dirs[i];
      byte p2 = DirectionUtils.construct(p, direction);
      if (PointUtils.isIn(p2) && isFree(map, p2)) {
        ret[index++] = p2;
      }
    }
    aroundPositionCache[p] = ByteUtils.copy(ret, index);
  }

  public static String toString(byte[] map) {
    String str = "";
    for (byte j = 0; j < FrameworkConstant.CASE_NB; j++) {
      if (j % FrameworkConstant.WIDTH == 0) {
        str += "\n";
      }
      byte v = map[j];
      String s;
      if (v == FrameworkConstant.WALL) {
        s = "#";
      } else if (v == FrameworkConstant.FREE) {
        s = ".";
      } else {
        if (v >= 10) v = 9;
        s = "" + v;
      }
      str += s;
    }
    return str;
  }

  public static byte[] extract(List<String> lines) {
    byte[] map = MapUtils.createNewMap();
    byte i = 0;
    for (String s : lines) {
      for (char c : s.toCharArray()) {
        fromInput(c, i, map);
        i++;
      }
    }
    return map;
  }

  public static byte[] extract(String line, int lineNum, byte[] map) {
    int i = lineNum * FrameworkConstant.WIDTH;
    for (int j = 0; j < FrameworkConstant.WIDTH; j++) {
      fromInput(line.charAt(j), i + j, map);
    }
    return map;
  }

  public static void fromInput(char c, int i, byte[] map) {
    byte v = -1;
    if (c == '#') {
      v = FrameworkConstant.WALL;
    } else if (c == '.') {
      v = FrameworkConstant.FREE;
    } else {
      throw new IllegalStateException("WRONG CHAR *" + c + "* " + i);
    }
    map[i] = v;
  }
}
