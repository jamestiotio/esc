package PerformanceTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

/**
 * You can modify this class as well.
 */
public class AddRemoveTest {
    protected CyclicBarrier barrier;
    protected Map<Integer, Integer> map;
    protected final int nTrials, nPairThreads;

    public static void main(String[] args) throws Exception {
        new AddRemoveTest(10, 10000).test(); // sample parameters
    }

    // Is this thread-safe?
    public AddRemoveTest(int nPairThreads, int ntrials) {
        this.map = new HashMap<>();// we are to change this map to others for testing their
                                   // performance.
        this.nTrials = ntrials;
        this.nPairThreads = nPairThreads;
        this.barrier = new CyclicBarrier(nPairThreads * 2 + 1);
    }

    void test() {
        try {
            for (int i = 0; i < nPairThreads; i++) {
                new Thread(new Adder()).start();
                new Thread(new Remover()).start();
            }
            barrier.await(); // wait for all threads to be ready
            barrier.await(); // wait for all threads to finish
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }

    class Adder implements Runnable {
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int) System.nanoTime());
                barrier.await();
                for (int i = nTrials; i > 0; --i) {
                    map.put(seed, seed);
                    seed = xorShift(seed);
                }
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Remover implements Runnable {
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int) System.nanoTime());
                barrier.await();
                for (int i = nTrials; i > 0; --i) {
                    Object[] keys = map.keySet().toArray();
                    if (keys.length > 0) {
                        map.remove(keys[Math.abs(seed) % keys.length]);
                    }
                    seed = xorShift(seed);
                }
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
