import java.util.concurrent.atomic.AtomicInteger;
// import java.util.concurrent.locks.ReentrantLock;

public class ExperimentFixed2 {
    // Either using AtomicInteger or a separate lock object should be fine. There is no need to use
    // both.
    private static AtomicInteger MY_INT = new AtomicInteger(0);
    // More memory is needed to be allocated to create the lock, and in terms of time, each thread
    // needs to wait until the lock is released before it can access it
    private static Object lock = new Object(); // A ReentrantLock could also be used
    // public static ReentrantLock mutex = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        new ChangeListener().start();
        System.out.println("Waiting two seconds so the JIT will probably optimize ChangeListener");
        Thread.sleep(2000);

        new ChangeMaker().start();
    }

    static class ChangeListener extends Thread {
        public void run() {
            AtomicInteger local_value;
            synchronized (lock) {
                // ExperimentFixed2.mutex.lock();
                local_value = new AtomicInteger(MY_INT.get());
                // ExperimentFixed2.mutex.unlock();
            }
            while (local_value.get() < 5) {
                synchronized (lock) {
                    // ExperimentFixed2.mutex.lock();
                    if (local_value.get() != MY_INT.get()) {
                        System.out.println("Got Change for MY_INT : " + MY_INT.get());
                        local_value = new AtomicInteger(MY_INT.get());
                    }
                    // ExperimentFixed2.mutex.unlock();
                }
            }
        }
    }

    static class ChangeMaker extends Thread {
        public void run() {
            AtomicInteger local_value;
            synchronized (lock) {
                // ExperimentFixed2.mutex.lock();
                local_value = new AtomicInteger(MY_INT.get());
                // ExperimentFixed2.mutex.unlock();
            }
            while (MY_INT.get() < 5) {
                synchronized (lock) {
                    // ExperimentFixed2.mutex.lock();
                    System.out.println("Incrementing MY_INT to " + (local_value.get() + 1));
                    MY_INT = new AtomicInteger(local_value.incrementAndGet());
                    // ExperimentFixed2.mutex.unlock();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
