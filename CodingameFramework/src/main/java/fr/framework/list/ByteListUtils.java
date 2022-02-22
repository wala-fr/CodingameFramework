package fr.framework.list;

import java.util.Arrays;
import fr.framework.AssertUtils;
import fr.framework.function.BytePredicate;

/**
 * copy/paste for IntListUtils, ShortListUtils...
 *
 */
public class ByteListUtils {

  public static void add(byte[] array, byte p) {
    array[size(array) + 1] = p;
    array[0]++;
  }

  public static void removeByValue(byte[] array, byte p) {
    boolean remove = false;
    for (byte i = 0; i < size(array); i++) {
      byte value = get(array, i);
      if (remove) {
        set(array, (byte) (i - 1), value);
      } else if (value == p) {
        remove = true;
      }
    }
    if (remove) {
      array[0]--;
    }
  }

  public static void removeByIndex(byte[] array, int index) {
    boolean remove = size(array) > index;
    for (byte i = (byte) (index + 1); i < size(array); i++) {
      byte value = get(array, i);
      set(array, (byte) (i - 1), value);
    }
    if (remove) {
      array[0]--;
    }
  }

  public static void clear(byte[] array) {
    array[0] = 0;
  }

  public static byte size(byte[] array) {
    return array[0];
  }

  public static boolean isEmpty(byte[] array) {
    return size(array) == 0;
  }

  public static byte get(byte[] array, int i) {
    return array[i + 1];
  }

  public static void set(byte[] array, byte i, byte newValue) {
    array[i + 1] = newValue;
  }

  public static boolean contains(byte[] array, byte i) {
    for (byte j = 0; j < size(array); j++) {
      if (get(array, j) == i) {
        return true;
      }
    }
    return false;
  }

  public static byte[] cut(byte[] array) {
    byte[] tmp = new byte[size(array)];
    System.arraycopy(array, 1, tmp, 0, tmp.length);
    return tmp;
  }

  public static void copy(byte[] source, byte[] destination) {
    System.arraycopy(source, 0, destination, 0, size(source) + 1);
  }
  
  public static void addAll(byte[] source, byte[] destination) {
    System.arraycopy(source, 1, destination, size(destination) + 1, size(source));
    destination[0] += source[0];
  }

  public static byte getLast(byte[] array) {
    byte size = size(array);
    AssertUtils.test(size > 0, "EMPTY BYTE LIST");
    return get(array, (byte) (size - 1));
  }

  public static void removeIf(byte[] array, BytePredicate pred) {
    byte size = size(array);
    byte index = 1;
    byte newSize = size;
    for (byte j = 0; j < size; j++) {
      byte p = get(array, j);
      if (pred.test(p)) {
        newSize--;
      } else {
        array[index++] = p;
      }
    }
    array[0] = newSize;
  }

  public static byte count(byte[] array, BytePredicate pred) {
    byte size = size(array);
    byte count = 0;
    for (byte j = 0; j < size; j++) {
      byte p = get(array, j);
      if (pred.test(p)) {
        count++;
      }
    }
    return count;
  }

  public static String toString(byte[] array) {
    return Arrays.toString(cut(array));
  }

}
