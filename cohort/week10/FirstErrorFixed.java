import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class B extends Thread {
    Random r = new Random();

    public void run() {
        try {
            Thread.sleep(r.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FirstErrorFixed.count.incrementAndGet();
    }
}


public class FirstErrorFixed {
    // Disallow concurrent access by multiple threads by using AtomicInteger to fix the race
    // condition
    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String args[]) {
        int numberofThreads = 10000;
        B[] threads = new B[numberofThreads];

        for (int i = 0; i < numberofThreads; i++) {
            threads[i] = new B();
            threads[i].start();
        }

        try {
            for (int i = 0; i < numberofThreads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            System.out.println("some thread is not finished");
        }

        System.out.print("The result is ... ");
        System.out.print("wait for it ... ");
        System.out.println(count);
    }
}
