import java.util.concurrent.atomic.AtomicInteger;

import java.util.Map;
import java.util.HashMap;

public class LockStaticVariablesFixed {
    public static AtomicInteger saving = new AtomicInteger(5000);
    public static AtomicInteger cash = new AtomicInteger(0);

    public static Map<String, AtomicInteger> assets = new HashMap<>();

    public static void main(String args[]) {
        assets.put("saving", new AtomicInteger(5000));
        assets.put("cash", new AtomicInteger(0));
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

        System.out
                .print("The result is: " + LockStaticVariablesFixed.assets.get("cash").intValue());
    }
}


class WD2 extends Thread {
    public void run() {
        synchronized (LockStaticVariablesFixed.assets) {
            if (LockStaticVariablesFixed.assets.get("saving").intValue() >= 1000) {
                try {
                    System.out.println("I am doing something.");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Only 5 threads will be able to make the necessary modifications
                LockStaticVariablesFixed.assets.put("saving", new AtomicInteger(
                        LockStaticVariablesFixed.assets.get("saving").intValue() - 1000));
                LockStaticVariablesFixed.assets.put("cash", new AtomicInteger(
                        LockStaticVariablesFixed.assets.get("cash").intValue() + 1000));
            }
        }
    }
}
