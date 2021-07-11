package code.utils.search;

import java.util.Arrays;
import java.util.NoSuchElementException;
import code.variable.Parameter;

/**
 * The element with the lowest score is always at the index 0. It's removed when an 'object' with a
 * highest score is added https://en.wikipedia.org/wiki/Heap_(data_structure)
 */
public class Heap {

  private static final int MAX_NB = Parameter.BEAM_NB;

  private int limit = MAX_NB;
  private byte[][] items = new byte[MAX_NB][];
  private double[] values = new double[MAX_NB];
  private int nb;

  public Heap() {}

  private void siftUp() {
    int k = nb - 1;
    while (k > 0) {
      int p = (k - 1) / 2;
      byte[] item = items[k];
      byte[] parent = items[p];
      double valueItem = values[k];
      double valueParent = values[p];

      if (valueItem < valueParent) {
        // swap
        items[k] = parent;
        items[p] = item;
        values[k] = valueParent;
        values[p] = valueItem;
        // move up one level
        k = p;
      } else {
        break;
      }
    }
  }

  public void insert(byte[] item, double value) {
    boolean add = true;
    if (nb == limit) {
      if (value > values[0]) {
        // insert
        delete();
      } else {
        add = false;
      }
    }
    if (add) {
      items[nb] = item;
      values[nb] = value;
      nb++;
      siftUp();
    }
  }

  private void siftDown() {
    int k = 0;
    int l = 2 * k + 1;
    while (l < nb) {
      int max = l;
      int r = l + 1;
      if (r < nb) { // there is a right child
        if (values[r] < values[l]) {
          max = r;
        }
      }
      if (values[k] > values[max]) {
        // switch
        byte[] temp = items[k];
        double tempValue = values[k];
        items[k] = items[max];
        values[k] = values[max];
        items[max] = temp;
        values[max] = tempValue;
        k = max;
        l = 2 * k + 1;
      } else {
        break;
      }
    }
  }

  public byte[] delete() throws NoSuchElementException {
    byte[] hold = items[0];
    items[0] = items[nb - 1];
    values[0] = values[nb - 1];
    nb--;
    siftDown();
    return hold;
  }

  public int size() {
    return nb;
  }

  public boolean isEmpty() {
    return nb == 0;
  }

  public void clear() {
    nb = 0;
  }

  public byte[] get(int j) {
    return items[j];
  }

  public double getValue(int j) {
    return values[j];
  }

  public void setLimit(int limit) {
    this.limit = Math.min(limit, MAX_NB);
  }

  @Override
  public String toString() {
    return nb + " " + Arrays.toString(Arrays.copyOf(values, nb));
  }
}
