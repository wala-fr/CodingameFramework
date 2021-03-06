package fr.framework.point;

import fr.framework.FrameworkConstant;
import fr.framework.logger.Logger;

public class DirectionUtils {

  private static final Logger logger = Logger.getLogger(DirectionUtils.class);

  public static String toString(Direction dir) {
    if (dir == null) {
      return null;
    }
    switch (dir) {
      case UP:
        return "N";
      case DOWN:
        return "S";
      case RIGHT:
        return "E";
      default:
        return "W";
    }
  }

  public static Direction fromString(String dir) {
    switch (dir) {
      case "N":
        return Direction.UP;
      case "S":
        return Direction.DOWN;
      case "E":
        return Direction.RIGHT;
      case "W":
        return Direction.LEFT;
      default:
        throw new IllegalStateException("invalid direction");
    }
  }

  public static Direction getDirectionTo(int p1, int p2) {
    int x = PointUtils.getX(p2) - PointUtils.getX(p1);
    if (x != 0) {
      x /= Math.abs(x);
    }
    int y = PointUtils.getY(p2) - PointUtils.getY(p1);
    if (y != 0) {
      y /= Math.abs(y);
    }
    for (Direction dir : Direction.values) {
      if (dir.getX() == x && dir.getY() == y) {
        return dir;
      }
    }
    throw new IllegalStateException(
        "no direction " + PointUtils.toPoint(p1) + " to " + PointUtils.toPoint(p2));
  }

  public static int construct(int p, Direction dir) {
    return construct(p, dir.getNum());
  }

  public static int construct(int p, int dir) {
    if (Direction.DOWN.is(dir)) {
      if (PointUtils.getY(p) == FrameworkConstant.HEIGHT - 1) {
        p = FrameworkConstant.OUT;
      } else {
        p += FrameworkConstant.WIDTH;
      }
    } else if (Direction.LEFT.is(dir)) {
      if (PointUtils.getX(p) == 0) {
        p = FrameworkConstant.OUT;
      } else {
        p--;
      }
    } else if (Direction.RIGHT.is(dir)) {
      if (PointUtils.getX(p) == FrameworkConstant.WIDTH - 1) {
        p = FrameworkConstant.OUT;
      } else {
        p++;
      }
    } else if (Direction.UP.is(dir)) {
      if (PointUtils.getY(p) == 0) {
        p = FrameworkConstant.OUT;
      } else {
        p -= FrameworkConstant.WIDTH;
      }
    } else {
      throw new IllegalStateException("no direction " + dir);
    }
    return p;
  }
}
