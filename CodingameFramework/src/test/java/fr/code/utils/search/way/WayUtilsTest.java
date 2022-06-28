package fr.code.utils.search.way;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Random;
import org.junit.Test;
import fr.framework.FrameworkConstant;
import fr.framework.logger.Logger;

public class WayUtilsTest {

  private static final Logger logger = Logger.getLogger(WayUtilsTest.class);

  @Test
  public void test1() {
    Random random = new Random();
    byte[] way = WayUtils.constructWay();
    for (int i = 0; i < 1_000_000; i++) {
      double value = Double.MAX_VALUE * random.nextDouble();
      WayUtils.setScore(way, value);
      assertEquals(value, WayUtils.getScore(way), 0);
    }
  }

  @Test
  public void test2() {
    byte[] way = WayUtils.constructWay();
    for (int i = 0; i < FrameworkConstant.CASE_NB; i++) {
      assertFalse(WayUtils.isDonePosition(way, i));
      WayUtils.setDonePosition(way, i);
      assertTrue(WayUtils.isDonePosition(way, i));
      logger.error(WayUtils.toMapString(way));
    }
  }
}
