import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Question for Cohort Exercise 1
 */
public class PerformanceExperiment {
    // Amdahl's Law limits the maximum speedup that can be attained/obtained by the fraction of the
    // program that must be executed serially/sequentially.
    private static final int NUM = 3; // TODO: Vary this number (N).
    private static final int POOLSIZE = 20;
    private static final CountDownLatch latch = new CountDownLatch(1);
    private static final CyclicBarrier barrier = new CyclicBarrier(NUM + 1);
    private static BigInteger n = new BigInteger("1127451830576035879");
    // private static BigInteger n = new BigInteger("239839672845043");
    private static final ExecutorService exec = Executors.newFixedThreadPool(POOLSIZE);

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM; i++) {
            final int j = i + 2;
            // A Runnable is a task (an abstract, discrete unit of work dynamically assigned to a
            // thread). Ideally, tasks have sensible boundaries and are independent to facilitate
            // concurrency. Computation decomposition should be balanced to maximize work-overhead
            // ratio and to maximize upper bound of speedup.
            exec.execute(new Runnable() {
                public void run() {
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    BigInteger i = new BigInteger(Integer.toString(j));
                    BigInteger stepSize = new BigInteger(Integer.toString(NUM));
                    BigInteger zero = new BigInteger("0");

                    while (true) {
                        // System.out.println("trying" + i);
                        if (Thread.currentThread().isInterrupted()) {
                            break;
                        }

                        if (n.remainder(i).compareTo(zero) == 0) {
                            if (latch.getCount() > 0) {
                                latch.countDown();
                                long endTime = System.currentTimeMillis();
                                System.out.println(
                                        "Time spent: " + (endTime - startTime) + " milliseconds.");
                            }
                        }

                        i = i.add(stepSize);
                    }
                }
            });
        }

        barrier.await();
        latch.await();
        exec.shutdownNow();
    }
}
