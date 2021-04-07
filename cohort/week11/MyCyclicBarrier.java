/**
 * Question of Cohort Exercise 1
 */

// We must ensure this class to be thread-safe. Why and how?
public class MyCyclicBarrier {
    private final int parties;
    private int count = 0;
    private Runnable torun; // When should we call this runnable task?

    public MyCyclicBarrier(int count, Runnable torun) {
        this.count = count;
        this.torun = torun;
        this.parties = count;
    }

    public MyCyclicBarrier(int count) {
        this.count = count;
        this.parties = count;
    }

    // TODO: complete the implementation below.
    // hint: use wait(), notifyAll()
    // Precondition: All threads in the party has arrived at the barrier, before any threads are
    // released.
    // Postcondition: The specified Runnable command is run once.
    public synchronized void await() {
        try {
            this.count--;
            if (this.count > 0) {
                this.wait();
            } else {
                this.notifyAll();
                if (this.torun != null)
                    this.torun.run();
                this.count = this.parties;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
