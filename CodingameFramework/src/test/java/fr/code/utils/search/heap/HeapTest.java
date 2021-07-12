package fr.code.utils.search.heap;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import fr.code.utils.search.heap.Heap;
import fr.framework.logger.Logger;

public class HeapTest {

  private static Logger logger = Logger.getLogger(HeapTest.class);

  @Test
  public void test1() {

    Heap heap = new Heap(0);
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

  @Test
  public void test2() {

    Heap heap = new Heap(0);
    heap.setLimit(9);
    
    insert(heap, 15);
    insert(heap, 9);
    insert(heap, 10);
    insert(heap, 8);
    insert(heap, 6);
    insert(heap, 3);
    insert(heap, 4);
    insert(heap, 2);
    insert(heap, 20);
    insert(heap, 30);
    insert(heap, -1);
  }

  private double insert(Heap heap, double value) {
    heap.insert(null, value);

    double min = Double.MAX_VALUE;
    for (int i = 0; i < heap.size(); i++) {
      min = Math.min(heap.getValue(i), min);
    }
    logger.error("value", value, "min", min, heap);
    assertEquals(min, heap.getValue(0), 0);
    return min;
  }
}
