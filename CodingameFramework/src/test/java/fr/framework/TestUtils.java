package fr.framework;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.powermock.reflect.Whitebox;
import fr.code.object.Game;
import fr.framework.logger.Logger;
import fr.framework.timer.Timer;

public class TestUtils {

  private static final Logger logger = Logger.getLogger(TestUtils.class);

  private static byte[] map;

  public static byte[] initMap(String fileName) throws IOException {
    List<String> lines = readMapFromFile(fileName);
    Whitebox.setInternalState(Game.getInstance(), "round", 1);
    Timer.getInstance().init();
    logger.error("round", Game.getInstance().getRound());

    FrameworkConstant.WIDTH = lines.get(0).length();
    FrameworkConstant.HEIGHT = lines.size();
    FrameworkConstant.CASE_NB = FrameworkConstant.WIDTH * FrameworkConstant.HEIGHT;
    logger.error(
        "width",
        FrameworkConstant.WIDTH,
        "height",
        FrameworkConstant.HEIGHT,
        "case nb",
        FrameworkConstant.CASE_NB);
    AssertUtils.test(FrameworkConstant.CASE_NB <= Byte.MAX_VALUE);
    map = MapUtils.extract(lines);
    logger.error(MapUtils.toString(map));
    return map;
  }

  private static List<String> readMapFromFile(String fileName) throws IOException {
    Path root = Paths.get("src\\test\\resources");
    Path path = root.resolve(fileName + ".txt");
    logger.error(path.toAbsolutePath());
    return Files.readAllLines(path);
  }
}
