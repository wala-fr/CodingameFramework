package fr.framework;

import fr.framework.logger.Logger;

public class AssertUtils {

  private static Logger logger = Logger.getLogger(AssertUtils.class);

  public static void test(boolean b, Object... o) {
    if (!b) {
      String str = StringUtils.toString(o);
      logger.error(str);
      throw new IllegalStateException(str);
    }
  }
}
