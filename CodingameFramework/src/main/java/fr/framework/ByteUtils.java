package fr.framework;

public class ByteUtils {

  public static byte[] copy(byte[] s) {
    byte[] d = new byte[s.length];
    copy(s, d);
    return d;
  }

  public static byte[] copy(byte[] array, int length) {
    byte[] newArray = new byte[length];
    System.arraycopy(array, 0, newArray, 0, length);
    return newArray;
  }

  public static void copy(byte[] s, byte[] d) {
    System.arraycopy(s, 0, d, 0, s.length);
  }

}
