package PerformanceTest;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

/**
 * Hints: Use the Barrier Timer for measurement.
 */
public class TimedTestConcMap extends AddRemoveTest {
    private BarrierTimer timer = new BarrierTimer();

    /**
     * You need to modify this constructor as well.
     * 
     * @param nPairThreads
     * @param trials
     */
    public TimedTestConcMap(int nPairThreads, int trials) {
        super(nPairThreads, trials);
        this.map = new ConcurrentHashMap<>();
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
            System.out.println("Throughput: " + timePerItem + " ns/item");  // Throughput is different compared to responsiveness
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        int tpt = 10000; // trials per thread
        System.out.println("Starting performance test for ConcurrentHashMap...");
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
