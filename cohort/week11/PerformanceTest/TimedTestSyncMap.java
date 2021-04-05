package PerformanceTest;

import java.util.Collections;
import java.util.HashMap;

/**
 * Hints: Use the Barrier Timer for measurement.
 */
public class TimedTestSyncMap extends AddRemoveTest {

    public TimedTestSyncMap(int nPairThreads, int trials) {
        super(nPairThreads, trials);
        this.map = Collections.synchronizedMap(new HashMap<Integer, Integer>());// we are to change
                                                                                // this map to
                                                                                // others for
                                                                                // testing their
                                                                                // performance.
    }

    /**
     * TODO: Overload the test(), and measure the performance.
     */
    public void test() {

    }

    public static void main(String[] args) throws Exception {
        int tpt = 10000; // trials per thread
        for (int cap = 1; cap <= 1000; cap *= 10) {
            System.out.println("Capacity: " + cap);
            for (int pairs = 1; pairs <= 128; pairs *= 2) {
                TimedTestSyncMap t = new TimedTestSyncMap(pairs, tpt);
                System.out.print("Pairs: " + pairs + "\t");
                t.test();
                System.out.print("\t");
                Thread.sleep(1000);
                t.test();
                System.out.println();
                Thread.sleep(1000);
            }
        }
    }
}
