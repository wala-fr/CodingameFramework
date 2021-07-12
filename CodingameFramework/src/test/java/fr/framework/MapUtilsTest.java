package fr.framework;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Test;
import fr.framework.logger.Logger;
import fr.framework.point.PointUtils;

public class MapUtilsTest {

  private static Logger logger = Logger.getLogger(MapUtilsTest.class);

  @Test
  public void test() throws IOException {

    byte[] map = TestUtils.initMap("map1");

    MapUtils.initCache(map);

    byte position = PointUtils.getPosition(2, 0);
    byte[] aroundPositions = MapUtils.getAroundPositions(position);
    logger.error(
        PointUtils.toPoint(position), "around positions", PointUtils.toPoint(aroundPositions));
    assertEquals(2, MapUtils.getNbAroundPositions(position));

    map[PointUtils.getPosition(1, 7)] = 2;
    logger.error(MapUtils.toString(map));

    assertEquals(4, MapUtils.getNbAroundPositions(PointUtils.getPosition(1, 7)));
  }
}
