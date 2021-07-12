package fr.framework.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import fr.framework.logger.Logger;

public class ByteListUtilsTest {

  private static Logger logger = Logger.getLogger(ByteListUtilsTest.class);
  
  @Test
  public void test() {
    byte[] list = new byte[10];
    ByteListUtils.add(list, (byte) 1); 
    ByteListUtils.add(list, (byte) -1); 
    ByteListUtils.add(list, (byte) 0); 
    ByteListUtils.add(list, (byte) -2); 
    ByteListUtils.add(list, (byte) 3);
    ByteListUtils.add(list, (byte) -2); 

    int size = 6;
    assertSize(size, list);
    
    assertEquals(-2, ByteListUtils.getLast(list));
    
    assertTrue(ByteListUtils.contains(list, (byte) -1));
    assertFalse(ByteListUtils.contains(list, (byte) -5));
    
    assertEquals(3, ByteListUtils.count(list, b -> b < 0));
    
    ByteListUtils.removeIf(list, b -> b < 0);
    size -= 3;
    assertSize(size, list);

    ByteListUtils.removeByIndex(list, 3);
    assertSize(size, list);
    
    ByteListUtils.removeByIndex(list, 5);
    assertSize(size, list);
    
    ByteListUtils.removeByIndex(list, 1);
    size--;
    assertSize(size, list);
    
    ByteListUtils.removeByValue(list, (byte) 5);
    assertSize(size, list);
    
    ByteListUtils.removeByValue(list, (byte) 3);
    size--;
    assertSize(size, list);
  }
  
  private void assertSize(int size, byte[] list) {
    assertEquals(size, ByteListUtils.size(list));
    logger.error(ByteListUtils.toString(list));
  }
}
