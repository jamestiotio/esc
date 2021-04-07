import java.util.concurrent.locks.ReentrantLock;
import java.util.Map;
import java.util.HashMap;

public class LockStaticVariablesFixed {
    public static int saving = 5000;
    public static int cash = 0;

    // ReentrantLock is almost always better than a synchronized block (more tools/utilities for the
    // belt)
    public static final ReentrantLock lock = new ReentrantLock();

    public static void main(String args[]) {
        int numberofThreads = 10000;
        WD2[] threads = new WD2[numberofThreads];

        for (int i = 0; i < numberofThreads; i++) {
            threads[i] = new WD2();
            threads[i].start();
        }

        try {
            for (int i = 0; i < numberofThreads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println("some thread is not finished");
        }

        System.out.print("The result is: " + LockStaticVariablesFixed.cash);
    }
}


class WD2 extends Thread {
    public void run() {
        LockStaticVariablesFixed.lock.lock();
        if (LockStaticVariablesFixed.saving >= 1000) {
            try {
                System.out.println("I am doing something.");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Only 5 threads will be able to make the necessary modifications
            LockStaticVariablesFixed.saving = LockStaticVariablesFixed.saving - 1000;
            LockStaticVariables.cash = LockStaticVariables.cash + 1000;
        }
        LockStaticVariablesFixed.lock.unlock();
    }
}
