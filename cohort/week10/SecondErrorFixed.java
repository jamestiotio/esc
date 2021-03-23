import java.util.concurrent.atomic.AtomicInteger;

public class SecondErrorFixed {
    public static AtomicInteger amount = new AtomicInteger(5000);

    public static void main(String args[]) {
        int numberofThreads = 10000;
        WDer2[] threads = new WDer2[numberofThreads];

        for (int i = 0; i < numberofThreads; i++) {
            threads[i] = new WDer2();
            threads[i].start();
        }

        int totalWithdraw = 0;

        try {
            for (int i = 0; i < numberofThreads; i++) {
                threads[i].join();
                totalWithdraw += threads[i].getResult();
            }
        } catch (InterruptedException e) {
            System.out.println("some thread is not finished");
        }

        System.out.print("The result is ... ");
        System.out.print("wait for it ... ");
        System.out.println(totalWithdraw);
    }
}


class WDer2 extends Thread {
    int whatIGot = 0;

    public void run() {
        synchronized (SecondErrorFixed.amount) {
            if (SecondErrorFixed.amount.intValue() >= 1000) {
                try {
                    System.out.println("I am doing something.");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SecondErrorFixed.amount.addAndGet(-1000);
                whatIGot = 1000;
            }
        }
    }

    public int getResult() {
        return whatIGot;
    }
}
