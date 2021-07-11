package code.utils;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.io.IOException;
import org.junit.BeforeClass;
import org.junit.Test;
import code.variable.Parameter;
import fr.framework.PointUtils;
import fr.framework.TestUtils;
import fr.framework.logger.Logger;

public class NextMoveUtilsTest {

  private static Logger logger = Logger.getLogger(NextMoveUtilsTest.class);

  private static byte[] map;
  
  @BeforeClass
  public static void setup() throws IOException {
    map = TestUtils.initMap("map1");
    Parameter.TIMEOUT_FIRST_ROUND = 1000_000;
    Parameter.WAY_MAX_LENGTH = 100;
  }
  
  @Test
  public void test1() {
    
    byte startPosition = PointUtils.getPosition(0, 0);
    byte endPosition = PointUtils.getPosition(2, 0);

    byte[] way = NextMoveUtils.proceed(startPosition, endPosition, map);
    assertNotNull(way);
  }
  
  @Test
  public void test2() {
    
    byte startPosition = PointUtils.getPosition(0, 0);
    byte endPosition = PointUtils.getPosition(0, 7);

    byte[] way = NextMoveUtils.proceed(startPosition, endPosition, map);
    assertNull(way);
  }
  
  @Test
  public void test3() {
    
    byte startPosition = PointUtils.getPosition(0, 0);
    byte endPosition = PointUtils.getPosition(6, 4);

    byte[] way = NextMoveUtils.proceed(startPosition, endPosition, map);
    assertNotNull(way);
  }
}
