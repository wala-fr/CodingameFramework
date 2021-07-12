package fr.code.utils.floodfill;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import fr.framework.FrameworkConstant;
import fr.framework.MapUtils;
import fr.framework.TestUtils;
import fr.framework.list.ByteListUtils;
import fr.framework.logger.Logger;
import fr.framework.point.PointUtils;

public class PlaceUtilsTest {

  private static Logger logger = Logger.getLogger(PlaceUtilsTest.class);

  @Test
  public void test() throws IOException {
    byte[] map = TestUtils.initMap("map2");
    MapUtils.initCache(map);
    byte placeNb = 2;
    int[] results = {0, 0, 14, 38, 4, 17};

    logger.error("START PLACE TESTS");
    for (byte p = 0; p < FrameworkConstant.CASE_NB; p++) {
      byte[] place = PlaceUtils.getPlace(p, map);
      if (!ByteListUtils.isEmpty(place)) {
        int size = ByteListUtils.size(place);
        for (int i = 0; i < size; i++) {
          byte p2 = ByteListUtils.get(place, i);
          map[p2] = placeNb;
        }
        assertEquals(results[placeNb], size);
        logger.error(placeNb, PointUtils.toPoint(p), MapUtils.toString(map));
        placeNb++;
      }
    }
  }
}
