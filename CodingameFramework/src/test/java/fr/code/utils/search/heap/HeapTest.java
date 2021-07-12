package fr.code.utils.search.heap;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import fr.framework.logger.Logger;

public class HeapTest {

  private static Logger logger = Logger.getLogger(HeapTest.class);
  
  private Heap heap = new Heap(0);

  // this list contains all the values added to the heap
  // it's then sorted to assure that the bigger elements have been kept in the heap
  private List<Double> list = new ArrayList<>();

  @Test
  public void test() {

    heap.setLimit(9);

    insert(15);
    insert(9);
    insert(10);
    insert(8);
    insert(6);
    insert(3);
    insert(4);
    insert(2);
    insert(20);
    insert(30);
    insert(-1);
    insert(25);
    insert(42);
    insert(-1);
    insert(-80);
    insert(200);
    insert(-10);
  }

  private void insert(double value) {
    heap.insert(null, value);
    list.add(value);
    Collections.sort(list);
    int minIndex = Math.max(0, list.size() - heap.getLimit());
    logger.error("minIndex", minIndex);

    double min = list.get(minIndex);
    logger.error("value", value, "min", min, heap);
    assertEquals(min, heap.getValue(0), 0);

    List<Double> tmpList = new ArrayList<>();
    for (int i = 0; i < heap.size(); i++) {
      tmpList.add(heap.getValue(i));
    }
    Collections.sort(tmpList);

    for (int i = minIndex; i < list.size(); i++) {
      assertEquals(list.get(i), tmpList.get(i - minIndex), 0);
    }
  }
}
