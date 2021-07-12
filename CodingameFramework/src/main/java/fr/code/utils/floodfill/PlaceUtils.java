package fr.code.utils.floodfill;

import fr.framework.FrameworkConstant;
import fr.framework.MapUtils;
import fr.framework.list.ByteListUtils;

public class PlaceUtils {

  private static final byte[] MAP_CALCULATE = new byte[FrameworkConstant.CASE_NB];
  private static final byte[] RET = new byte[FrameworkConstant.CASE_NB + 1];
  private static final byte[][] LISTS = new byte[2][];
  private static final byte DONE = 2;

  static {
    for (int i = 0; i < LISTS.length; i++) {
      LISTS[i] = new byte[FrameworkConstant.CASE_NB + 1];
    }
  }

  public static int getPlaceSize(byte start, byte[] map) {
    return ByteListUtils.size(getPlace(start, map, Integer.MAX_VALUE));
  }

  public static byte[] getPlace(byte start, byte[] map) {
    return getPlace(start, map, Integer.MAX_VALUE);
  }

  public static byte[] getPlace(byte start, byte[] map, int limit) {
    ByteListUtils.clear(RET);
    if (!MapUtils.isFree(map, start)) {
      return RET;
    }

    MapUtils.copy(map, MAP_CALCULATE);
    MAP_CALCULATE[start] = DONE;
    ByteListUtils.add(RET, start);

    byte[] set = LISTS[0];
    ByteListUtils.clear(set);
    ByteListUtils.add(set, start);

    int l = 0;
    while (!ByteListUtils.isEmpty(set) && l < limit) {
      l++;
      byte[] tmp = LISTS[l % 2];
      ByteListUtils.clear(tmp);
      int size = ByteListUtils.size(set);
      for (int j = 0; j < size; j++) {
        byte p = ByteListUtils.get(set, j);
        byte[] nexts = MapUtils.getAroundPositions(p);
        for (int i = 0; i < nexts.length; i++) {
          byte p2 = nexts[i];
          if (MapUtils.isFree(MAP_CALCULATE, p2)) {
            ByteListUtils.add(tmp, p2);
            MAP_CALCULATE[p2] = DONE;
            ByteListUtils.add(RET, p2);
          }
        }
      }
      set = tmp;
    }
    return RET;
  }
}
