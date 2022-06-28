package fr.framework;

public class FrameworkConstant {

  // height and width might be defined in the game's inputs
  public static int HEIGHT = 8;
  public static int WIDTH = 8;
  public static int CASE_NB = HEIGHT * WIDTH;

  public static final int OUT = -1;

  public static final int FREE = 0;
  public static final int WALL = 1;
  // could be more than two values (FREE, WALL, PLAYER_BASE, TAVERN...) that why i used byte array
  // for map and not bitboards

}
