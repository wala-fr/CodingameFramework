package fr.framework;

import org.junit.Test;
import code.utils.search.Heap;
import fr.framework.logger.Logger;

public class HeapTest {

  private static Logger logger = Logger.getLogger(HeapTest.class);

  @Test
  public void test() {

    Heap heap = new Heap();
    heap.setLimit(9);
    heap.insert(null, 15);
    heap.insert(null, 12);
    heap.insert(null, 9);
    
    heap.insert(null, 10);
    heap.insert(null, 8);
    heap.insert(null, 6);
    
    heap.insert(null, 3);
    heap.insert(null, 4);
    heap.insert(null, 2);

    logger.error(heap);
    
    heap.insert(null, -1);
    logger.error(heap);

    heap.insert(null, 7);
    logger.error(heap);
  }
}
