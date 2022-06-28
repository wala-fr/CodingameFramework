package fr.code.utils.search.heap;

public class HeapCache {

  private static final Heap[] CACHE = new Heap[2];

  static {
    for (int i = 0; i < CACHE.length; i++) {
      Heap tmp = new Heap(i);
      CACHE[i] = tmp;
    }
  }

  public static Heap get(int num) {
    Heap ret = CACHE[num];
    return ret;
  }

  public static void setLimit(int limit) {
    CACHE[0].setLimit(limit);
    CACHE[1].setLimit(limit);
  }
}
