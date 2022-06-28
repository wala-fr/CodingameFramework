package fr.framework;

public class ByteUtils {

  public static byte[] copy(byte[] array) {
    return copy(array, array.length);
  }

  public static byte[] copy(byte[] array, int length) {
    byte[] ret = new byte[length];
    System.arraycopy(array, 0, ret, 0, length);
    return ret;
  }

  public static void copy(byte[] source, byte[] destination) {
    System.arraycopy(source, 0, destination, 0, source.length);
  }
}
