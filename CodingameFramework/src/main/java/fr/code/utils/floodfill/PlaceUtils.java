package fr.code.utils.floodfill;

import fr.framework.FrameworkConstant;
import fr.framework.MapUtils;
import fr.framework.list.ByteListUtils;

public class PlaceUtils {

  private static final int[] DONE_MAP = new int[FrameworkConstant.CASE_NB];
  private static final byte[] RET_LIST = new byte[FrameworkConstant.CASE_NB + 1];
  private static final byte[][] TMP_LISTS = new byte[2][];
  private static int DONE_COUNT = 0;

  static {
    for (int i = 0; i < TMP_LISTS.length; i++) {
      TMP_LISTS[i] = new byte[FrameworkConstant.CASE_NB + 1];
    }
  }

  public static int getPlaceSize(byte start, byte[] map) {
    return ByteListUtils.size(getPlace(start, map, Integer.MAX_VALUE));
  }

  public static byte[] getPlace(byte start, byte[] map) {
    return getPlace(start, map, Integer.MAX_VALUE);
  }

  public static byte[] getPlace(byte start, byte[] map, int limit) {
    ByteListUtils.clear(RET_LIST);
    if (!MapUtils.isFree(map, start)) {
      return RET_LIST;
    }

    incrementDoneCount();
    DONE_MAP[start] = DONE_COUNT;
    ByteListUtils.add(RET_LIST, start);

    byte[] current = TMP_LISTS[0];
    ByteListUtils.clear(current);
    ByteListUtils.add(current, start);

    int l = 0;
    while (!ByteListUtils.isEmpty(current) && l < limit) {
      l++;
      byte[] next = TMP_LISTS[l % 2];
      ByteListUtils.clear(next);
      int size = ByteListUtils.size(current);
      for (int j = 0; j < size; j++) {
        byte p = ByteListUtils.get(current, j);
        byte[] nexts = MapUtils.getAroundPositions(p);
        for (int i = 0; i < nexts.length; i++) {
          byte p2 = nexts[i];
          if (DONE_MAP[p2] != DONE_COUNT) {
            ByteListUtils.add(next, p2);
            DONE_MAP[p2] = DONE_COUNT;
            ByteListUtils.add(RET_LIST, p2);
          }
        }
      }
      current = next;
    }
    return RET_LIST;
  }

  private static void incrementDoneCount() {
    DONE_COUNT++;
    if (DONE_COUNT == 0) {
      for (int i = 0; i < DONE_MAP.length; i++) {
        DONE_MAP[i] = 0;
      }
      DONE_COUNT++;
    }
  }
}
