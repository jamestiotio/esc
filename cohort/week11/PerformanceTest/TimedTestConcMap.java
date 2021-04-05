package PerformanceTest;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Hints: Use the Barrier Timer for measurement.
 */
public class TimedTestConcMap extends AddRemoveTest {

    public TimedTestConcMap(int nPairThreads, int trials) {
        super(nPairThreads, trials);
        this.map = new ConcurrentHashMap<>();
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
                TimedTestConcMap t = new TimedTestConcMap(pairs, tpt);
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
