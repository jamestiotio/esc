import java.util.Random;

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


class A extends Thread {
    Random r = new Random();

    public void run() {
        try {
            Thread.sleep(r.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // This is not an atomic step in machine/assembly language (hence race condition since need
        // to LD, ADD and ST)
        // Java specifications also do not guarantee that primitive variable assignments are always
        // atomic for long and double types
        // JVM may also change execution ordering if it improves and optimizes the execution
        // efficiency of the Java
        // bytecode (both between threads and between sequential statements), as long as the initial
        // logic was not violated/different
        // To fix the error, need to implement the usage of a mutex lock
        FirstError.count++;
    }
}
