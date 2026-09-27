package fr.framework;

public class ArrayUtils {

  private static final int CAPACITY_MAX = 1000;
  private static final int[] CLEAN = new int[CAPACITY_MAX];
  private static final int[] CLEAN2 = new int[CAPACITY_MAX];

  static {
    for (int i = 0; i < CLEAN2.length; i++) {
      CLEAN2[i] = -1;

    }
  }

  public static void clean(int[] map) {
    copy(CLEAN, map);
  }
  
  public static void clean2(int[] map) {
    copy(CLEAN2, map);
  }

  public static void copy(int[] s, int[] d) {
    System.arraycopy(s, 0, d, 0, d.length);
  }
  
  public static void copy(int[] s, int[] d, int l) {
    System.arraycopy(s, 0, d, 0, l);
  }
}
