import java.math.BigInteger;

public class FactorThreadNoInterrupt {
    // Define desired thread count/number of threads (time complexity: O(n/k), where k is the number
    // of threads)
    public static final int THREAD_COUNT = 16;

    public static void main(String[] args) {
        BigInteger result = null;

        // Define desired semiprime to be factorized (other non-prime positive integers could
        // technically also be factorized, but only to their first found two factors)
        final BigInteger n = new BigInteger("1127451830576035879");

        if (n.compareTo(new BigInteger("1")) <= 0) {
            System.out.println("Invalid input!");
            System.exit(1);
        }

        // Create array of factorizers
        final Factorizer[] factorizers = new Factorizer[THREAD_COUNT];

        // Create array of threads
        final Thread[] factorThreads = new Thread[THREAD_COUNT];

        // Assign values to each thread and start them
        for (int i = 0; i < THREAD_COUNT; i++) {
            // We start from 2 (since 2 is the smallest prime integer)
            factorizers[i] = new Factorizer(n, i + 2, THREAD_COUNT);
            factorThreads[i] = new Thread(factorizers[i]);
            System.out.println("Starting thread " + (i + 1) + "...");
            factorThreads[i].start();
        }

        // Wait for any thread to finish and print the result
        for (int i = 0; i < THREAD_COUNT; i++) {
            try {
                factorThreads[i].join();
            } catch (InterruptedException e) {
                System.out.println("Thread " + (i + 1) + " was interrupted.");
                e.printStackTrace();
            }

            if (factorizers[i].getResult() != null) {
                result = factorizers[i].getResult();
                break;
            }
        }

        if (result == null) {
            System.out.println("The specified integer is a prime, not a semiprime!");
            System.exit(1);
        }

        BigInteger otherFactor = n.divide(result);

        System.out.println("Two factors of " + n + " are: " + result + " and " + otherFactor + ".");
    }
}


// Precondition: n is a semi-prime number.
// Postcondition: the result is a prime factor of n.
class Factorizer implements Runnable {
    private BigInteger n, start, step, result;
    private static boolean stop = false;

    public Factorizer(final BigInteger n, final int start, final int step) {
        this.n = n;
        this.start = BigInteger.valueOf(start);
        this.step = BigInteger.valueOf(step);
    }

    public BigInteger getResult() {
        return this.result;
    }

    public void run() {
        this.factor();
    }

    public void factor() {
        final BigInteger zero = new BigInteger("0");

        while (this.start.compareTo(this.n) < 0) {
            if (stop)
                break;
            if (this.n.remainder(this.start).compareTo(zero) == 0) {
                this.result = this.start;
                System.out.println("A factor is found!");
                stop = true;
                break;
            }

            this.start = this.start.add(this.step);
        }
    }
}
