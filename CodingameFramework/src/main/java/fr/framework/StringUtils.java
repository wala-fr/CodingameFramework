package fr.framework;

public class StringUtils {

  public static String toString(Object... p) {
    StringBuilder str = new StringBuilder();
    int i = 0;
    for (Object o : p) {
      str.append(o == null ? "null" : o.toString());
      if (++i < p.length) {
        str.append(' ');
      }
    }
    return str.toString();
  }
}
