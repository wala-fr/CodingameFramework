package code.utils.search;

public class HeapStock {

  private static final HeapStock[] CACHE = new HeapStock[2];

  private final Heap heap = new Heap();
  private int num;

  static {
    for (int i = 0; i < CACHE.length; i++) {
      HeapStock tmp = new HeapStock(i);
      CACHE[i] = tmp;
    }
  }

  private HeapStock(int num) {
    this.num = num;
  }

  public void reset() {
    heap.clear();
  }

  public void add(byte[] way, double score) {
    heap.insert(way, score);
  }

  public int getCacheIndex() {
    return num;
  }

  public boolean isEmpty() {
    return heap.isEmpty();
  }

  public int size() {
    return heap.size();
  }

  public static HeapStock get(int num) {
    HeapStock ret = CACHE[num];
    ret.reset();
    return ret;
  }

  public byte[] getObject(int j) {
    return heap.get(j);
  }

  public double getScore(int j) {
    return heap.getValue(j);
  }
  
  @Override
  public String toString() {
    return "WayStock [size=" + heap.size() + ", cacheIndex=" + num + "]";
  }
}
