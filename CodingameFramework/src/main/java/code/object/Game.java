package code.object;

import java.util.Scanner;
import fr.framework.logger.Logger;
import fr.framework.timer.Timer;

public class Game {

  private static Logger logger = Logger.getLogger(Game.class);

  private static final Game instance = new Game();

  private int round = 0;

  private Game() {}

  public static Game getInstance() {
    return instance;
  }

  public void init(Scanner in) {}

  public void update(Scanner in) {
    round++;
    // read first game input
    Timer.getInstance().init();
    logger.error("round", round);
    // read next game inputs
   
  }

  public boolean isFirstRound() {
    return round == 1;
  }

  public int getRound() {
    return round;
  }
}
