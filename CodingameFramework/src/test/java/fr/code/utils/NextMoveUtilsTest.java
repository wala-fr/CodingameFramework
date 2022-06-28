package fr.code.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import fr.code.variable.Parameter;
import fr.framework.TestUtils;
import fr.framework.logger.Logger;
import fr.framework.point.PointUtils;

public class NextMoveUtilsTest {

  private static final Logger logger = Logger.getLogger(NextMoveUtilsTest.class);

  private static byte[] map;

  @BeforeClass
  public static void setup() throws IOException {
    map = TestUtils.initMap("map1");
    Parameter.TIMEOUT_FIRST_ROUND = 1000_000;
    Parameter.BEAM_MAX_DEPTH = 100;
  }

  @Test
  public void test1() {

    int startPosition = PointUtils.getPosition(0, 0);
    int endPosition = PointUtils.getPosition(2, 0);

    byte[] way = NextMoveUtils.proceed(startPosition, endPosition, map);
    assertNotNull(way);
  }

  @Test
  public void test2() {
    int startPosition = PointUtils.getPosition(0, 0);
    int endPosition = PointUtils.getPosition(0, 7);

    byte[] way = NextMoveUtils.proceed(startPosition, endPosition, map);
    assertNull(way);
  }

  @Test
  public void test3() {
    int startPosition = PointUtils.getPosition(0, 0);
    int endPosition = PointUtils.getPosition(6, 4);

    byte[] way = NextMoveUtils.proceed(startPosition, endPosition, map);
    assertNotNull(way);
  }

  @Test
  public void test4() {
    // start in wall
    int startPosition = PointUtils.getPosition(1, 0);
    int endPosition = PointUtils.getPosition(0, 7);

    byte[] way = NextMoveUtils.proceed(startPosition, endPosition, map);
    assertNull(way);
  }
}
