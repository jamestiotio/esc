import java.util.concurrent.CountDownLatch;

/**
 * Question of Cohort Exercise 2
 */

public class CountDownLatchExerciseQns {
    public static void main(String args[]) throws InterruptedException {
        final int limit = 7;
        final int noOfSearcher = 4;
        final CountDownLatch latch = new CountDownLatch(limit); // This is used as the
                                                                // recorder/ledger.
        final CountDownLatch finish = new CountDownLatch(noOfSearcher); // This is used as the
                                                                        // sequence controller.
        String[] array = new String[] {"A", "B", "F", "D", "A", "B", "F", "D", "A", "B", "F", "D",
                "A", "B", "F", "D", "A", "B", "F", "D", "A", "B", "F", "D", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F",
                "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F", "F"};

        final Searcher[] searchers = new Searcher[noOfSearcher];

        // Creating all the searcher threads and start them to search for "F"
        for (int i = 0; i < noOfSearcher; i++) {
            if (latch.getCount() > 0) {
                searchers[i] = new Searcher(array, i * array.length / noOfSearcher,
                        (i + 1) * array.length / noOfSearcher, latch, finish);
                searchers[i].start();
            } else {
                finish.countDown();
            }
        }

        // Creating the awaitThread to wait for searchers to finish searching
        Thread awaitThread = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("awaitThread awaiting");
                    latch.await(); // What is the purpose of this await? What is it waiting for?
                    System.out.println("awaitThread finishing");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } // Main thread is waiting on CountDownLatch to finish

                // Attempt to encourage scheduler to schedule other threads more erratically in the
                // meantime to show that there is no race condition present
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // Force all searchers to stop now
                for (int i = 0; i < noOfSearcher; i++) {
                    searchers[i].interrupt();
                }
                // Force all searchers to stop now
                while (finish.getCount() > 0) {
                    finish.countDown();
                }
            }
        });
        awaitThread.start();

        System.out.println("main Thread awaiting");
        finish.await();
        System.out.println("main Thread finishing");
        if (latch.getCount() == 0)
            System.out.println("Exactly " + limit
                    + " Fs have been found. All threads are terminated before more Fs are found.");
        while (latch.getCount() > 0) {
            latch.countDown();
        }
    }
}


class Searcher extends Thread {
    private final String[] data;
    private final int start;
    private final int end;
    private final CountDownLatch latch;
    private final CountDownLatch finish;

    public Searcher(String[] data, int start, int end, CountDownLatch latch,
            CountDownLatch finish) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.latch = latch;
        this.finish = finish;
    }

    /**
     * Fill up this method. check for "F".
     */
    public void run() {
        for (int i = this.start; i < this.end; i++) {
            if (this.isInterrupted()) {
                System.out.println("Thread is interrupted!");
                break;
            }

            else if (latch.getCount() == 0) {
                this.interrupt();
            }

            else if (this.data[i].equalsIgnoreCase("F")) {
                latch.countDown();
                System.out.println("An F is found at position " + i + ".");
            }
        }

        finish.countDown();
    }
}
