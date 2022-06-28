package fr.framework.random;

import java.util.Random;

/**
 * @see <a href="https://www.javamex.com/tutorials/random_numbers/xorshift.shtml">Javamex XORShift
 *     random number generators</a>
 * @see <a
 *     href="https://github.com/apache/flink/blob/master/flink-core/src/main/java/org/apache/flink/util/XORShiftRandom.java">Apache
 *     XORShiftRandom.java</a>
 */
public class XORShiftRandom extends Random {

  private long seed = System.nanoTime();

  public XORShiftRandom() {}

  @Override
  protected int next(int bits) {
    // N.B. Not thread-safe!
    long x = this.seed;
    x ^= (x << 21);
    x ^= (x >>> 35);
    x ^= (x << 4);
    this.seed = x;
    x &= ((1L << bits) - 1);
    return (int) x;
  }
}
