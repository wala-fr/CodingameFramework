package fr.framework.logger;

public class Timer {

  private static Timer instance = new Timer();

  private long timeStart;

  private Timer() {}

  public static Timer getInstance() {
    return instance;
  }

  public void init() {
    timeStart = System.currentTimeMillis();
  }

  public long getTime() {
    return System.currentTimeMillis() - timeStart;
  }
}
