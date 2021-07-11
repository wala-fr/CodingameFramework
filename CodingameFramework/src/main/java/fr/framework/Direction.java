package fr.framework;

public enum Direction {
  UP(0, -1, 0),
  DOWN(0, 1, 1),
  LEFT(-1, 0, 2),
  RIGHT(1, 0, 3);
  private byte num;
  private int x;
  private int y;

  private static Direction[] numToDirection = new Direction[4];

  static {
    Direction[] values = Direction.values();
    for (int i = 0; i < values.length; i++) {
      Direction value = values[i];
      numToDirection[value.getNum()] = value;
    }
  }

  private Direction(int x, int y, int num) {
    this.x = x;
    this.y = y;
    this.num = (byte) num;
  }

  public byte getNum() {
    return num;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Direction getOpposite() {
    switch (this) {
      case LEFT:
        return Direction.RIGHT;
      case RIGHT:
        return Direction.LEFT;
      case UP:
        return Direction.DOWN;
      default:
        return UP;
    }
  }

  public static Direction fromNum(byte num) {
    return numToDirection[num];
  }

  public boolean is(byte num) {
    return num == this.num;
  }
}