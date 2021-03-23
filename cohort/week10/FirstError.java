import java.util.Random;

class A extends Thread {
    Random r = new Random();

    public void run() {
        try {
            Thread.sleep(r.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FirstError.count++;
    }
}


public class FirstError {
    public static int count = 0;

    public static void main(String args[]) {
        int numberofThreads = 10000;
        A[] threads = new A[numberofThreads];

        for (int i = 0; i < numberofThreads; i++) {
            threads[i] = new A();
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
