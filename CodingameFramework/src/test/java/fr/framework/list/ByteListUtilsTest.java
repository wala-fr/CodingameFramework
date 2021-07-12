package fr.framework.list;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.junit.Test;
import fr.framework.logger.Logger;

public class ByteListUtilsTest {

  private static Logger logger = Logger.getLogger(ByteListUtilsTest.class);
  
  @Test
  public void test() {
    byte[] tmp = new byte[10];
    ByteListUtils.add(tmp, (byte) 1); 
    ByteListUtils.add(tmp, (byte) -1); 
    ByteListUtils.add(tmp, (byte) 0); 
    ByteListUtils.add(tmp, (byte) -2); 
    ByteListUtils.add(tmp, (byte) 3);
    ByteListUtils.add(tmp, (byte) -2); 

    logger.error(ByteListUtils.toString(tmp));
    assertEquals(6, ByteListUtils.size(tmp));

    int size = ByteListUtils.size(tmp);
    ByteListUtils.removeIf(tmp, b -> b < 0);
    assertEquals(size - 3, ByteListUtils.size(tmp));
    logger.error(ByteListUtils.toString(tmp));

    size = ByteListUtils.size(tmp);
    ByteListUtils.removeByIndex(tmp, 3);
    assertEquals(size, ByteListUtils.size(tmp));
    logger.error(ByteListUtils.toString(tmp));

    
    ByteListUtils.removeByIndex(tmp, 5);
    assertEquals(size, ByteListUtils.size(tmp));
    logger.error(ByteListUtils.toString(tmp));
    
    ByteListUtils.removeByIndex(tmp, 1);
    assertEquals(size - 1, ByteListUtils.size(tmp));
    logger.error(ByteListUtils.toString(tmp));
  }
}
