package fr.code;

import java.util.Scanner;
import fr.code.utils.NextMoveUtils;
import fr.code.utils.Utils;

public class Player extends Utils {

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    game.init(in);
    while (true) {
      game.update(in);
      NextMoveUtils.proceed();
      printAction();
    }
  }
}
