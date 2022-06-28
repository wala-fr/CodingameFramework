package fr.framework.logger;

import fr.framework.timer.Timer;

public class Logger {
  public enum Level {
    DEBUG,
    ERROR,
    NONE;
  }

  // set to NONE to deactivate all loggers
  private static final Level LEVEL = Level.ERROR;
  private Level levelSpecific;

  private Class<?> clazz;

  private Logger(Class<?> clazz) {
    this.clazz = clazz;
  }

  public static Logger getLogger(Class<?> clazz) {
    return new Logger(clazz);
  }

  public static Logger getLogger(Class<?> clazz, Level levelSpecific) {
    Logger ret = new Logger(clazz);
    ret.levelSpecific = levelSpecific;
    return ret;
  }

  public void error(Object... s) {
    print(s);
  }

  public void debug(Object... s) {
    if (getLevel() == Level.DEBUG) {
      print(s);
    }
  }

  private static final StringBuilder sb = new StringBuilder(5000);

  public void print(Object... s) {
    if (getLevel() == Level.NONE) {
      return;
    }
    sb.delete(0, sb.length());
    sb.append(Timer.getInstance().getTime());
    sb.append(' ');
    sb.append(clazz.getSimpleName());
    sb.append(" : ");
    int i = 0;
    for (Object o : s) {
      sb.append(o == null ? "null" : o.toString());
      if (++i < s.length) {
        sb.append(' ');
      }
    }
    System.err.println(sb);
  }

  private Level getLevel() {
    if (levelSpecific == null || LEVEL == Level.NONE) {
      return LEVEL;
    }
    return levelSpecific;
  }
}
