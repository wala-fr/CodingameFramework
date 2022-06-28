package fr.framework.point;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import fr.framework.FrameworkConstant;

public class PointUtils {

  private static Point[] pointCache;

  private static final Point OUT = new Point(-1, -1);

  public static Point toPoint(int p) {
    if (p == FrameworkConstant.OUT) {
      return OUT;
    }
    if (pointCache == null) {
      pointCache = new Point[FrameworkConstant.CASE_NB];
    }
    Point ret = pointCache[p];
    if (ret == null) {
      ret = new Point(getX(p), getY(p));
      pointCache[p] = ret;
    }
    return ret;
  }

  /** for logging purpose */
  public static List<Point> toPoint(byte[] array) {
    return IntStream.range(0, array.length)
        .mapToObj(i -> array[i])
        .map(PointUtils::toPoint)
        .collect(Collectors.toList());
  }

  /** for logging purpose */
  public static List<Point> toPoint(Collection<Byte> collection) {
    return collection.stream().map(PointUtils::toPoint).collect(Collectors.toList());
  }

  public static int getPosition(Scanner in) {
    int x = in.nextInt();
    int y = in.nextInt();
    return getPosition(x, y);
  }

  public static int getPosition(int x, int y) {
    return x + FrameworkConstant.WIDTH * y;
  }

  public static int distance(int p1, int p2) {
    return getDeltaX(p1, p2) + getDeltaY(p1, p2);
  }

  private static int getDeltaX(int p1, int p2) {
    return Math.abs(getX(p1) - getX(p2));
  }

  private static int getDeltaY(int p1, int p2) {
    return Math.abs(getY(p1) - getY(p2));
  }

  public static boolean isNextTo(int p1, int p2) {
    return distance(p1, p2) <= 1;
  }

  public static int getX(int p) {
    return p % FrameworkConstant.WIDTH;
  }

  public static int getY(int p) {
    return p / FrameworkConstant.WIDTH;
  }

  public static String toXYString(int p) {
    return getX(p) + " " + getY(p);
  }

  public static boolean isIn(int p) {
    return p >= 0 && p < FrameworkConstant.CASE_NB;
  }
}
