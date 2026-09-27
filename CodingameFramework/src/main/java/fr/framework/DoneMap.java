package fr.framework;

public class DoneMap {

  private final int[] map;
  private int count = 0;

  public DoneMap() {
    this(FrameworkConstant.CASE_NB + 1);
  }

  public DoneMap(int capacity) {
    map = new int[capacity];
    reset();
  }

  public void reset() {
    count++;
    if (count == 0) {
      ArrayUtils.clean(map);
      count++;
    }
  }

  public boolean isDone(int p) {
    return map[p] == count;
  }

  public boolean setDone(int p) {
    if (map[p] == count) {
      return false;
    }
    map[p] = count;
    return true;
  }

  public boolean undo(int p) {
    if (map[p] != count) {
      return false;
    }
    map[p] = count - 1;
    return true;
  }
}
