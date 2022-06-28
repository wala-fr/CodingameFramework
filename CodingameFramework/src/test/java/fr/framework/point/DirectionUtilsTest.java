package fr.framework.point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import fr.framework.FrameworkConstant;
import fr.framework.logger.Logger;

public class DirectionUtilsTest {

  private static final Logger logger = Logger.getLogger(DirectionUtilsTest.class);

  @Test
  public void test() {
    for (byte p = 0; p < FrameworkConstant.CASE_NB; p++) {
      for (Direction dir : Direction.values()) {
        int nextPosition = DirectionUtils.construct(p, dir);
        if (nextPosition != FrameworkConstant.OUT) {
          assertEquals(dir, DirectionUtils.getDirectionTo(p, nextPosition));
        } else {
          int x = PointUtils.getX(p);
          int y = PointUtils.getY(p);
          logger.error(x, y, dir);
          assertTrue(
              x == 0
                  || x == FrameworkConstant.WIDTH - 1
                  || y == 0
                  || y == FrameworkConstant.HEIGHT - 1);
        }
      }
    }
  }
}
