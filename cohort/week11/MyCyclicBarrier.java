/**
 * Question of Cohort Exercise 1
 */

// we must ensure this class to be thread-safe. why?
public class MyCyclicBarrier {
    private int count = 0;
    private Runnable torun;
    private int initial = 0;

    public MyCyclicBarrier(int count, Runnable torun) {
        this.count = count;
        this.initial = count;
        this.torun = torun;
    }

    public MyCyclicBarrier(int count) {
        this.count = count;
        this.initial = count;
    }

    // TODO: complete the implementation below.
    // hint: use wait(), notifyAll()
    public synchronized void await() {
        try {
            this.count--;
            if (this.count > 0) {
                this.wait();
            } else {
                this.notifyAll();
                if (this.torun != null) this.torun.run();
                this.count = this.initial;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
