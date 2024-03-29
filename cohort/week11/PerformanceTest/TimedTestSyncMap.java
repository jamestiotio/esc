package PerformanceTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;

/**
 * Hints: Use the Barrier Timer for measurement.
 */
public class TimedTestSyncMap extends AddRemoveTest {
    private BarrierTimer timer = new BarrierTimer();

    /**
     * TODO
     * You can modify this constructor as well.
     * 
     * @param nPairThreads
     * @param trials
     */
    public TimedTestSyncMap(int nPairThreads, int trials, int capacity) {
        super(nPairThreads, trials);
        this.map = Collections.synchronizedMap(new HashMap<Integer, Integer>(capacity));
        this.barrier = new CyclicBarrier(nPairThreads * 2 + 1, this.timer);
    }

    /**
     * TODO: Overload the test(), and measure the performance.
     */
    public void test() {
        try {
            this.timer.clear();
            for (int i = 0; i < nPairThreads; i++) {
                new Thread(new Adder()).start();
                new Thread(new Remover()).start();
            }
            this.barrier.await();
            this.barrier.await();
            long timePerItem = this.timer.getTime() / (nPairThreads * (long) nTrials);
            System.out.println("Latency: " + timePerItem + " ns/item");
            System.out.println("Throughput: " + (1 / timePerItem) + " items/ns"); // Throughput is different compared to responsiveness
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Collections.synchronizedMap is faster in general.
    public static void main(String[] args) throws Exception {
        int tpt = 10000; // trials per thread
        System.out.println("Starting performance test for Collections.synchronizedMap...");
        for (int cap = 1; cap <= 1000; cap *= 10) {
            System.out.println("Initial capacity: " + cap);
            for (int pairs = 1; pairs <= 128; pairs *= 2) {
                TimedTestSyncMap t = new TimedTestSyncMap(pairs, tpt, cap);
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
