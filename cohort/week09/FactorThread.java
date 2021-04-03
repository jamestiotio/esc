import java.math.BigInteger;

public class FactorThread {
    // Define desired thread count/number of threads (time complexity: O(n/k), where k is the number
    // of threads)
    public static final int NUMBER_OF_THREADS = 16;
    // There is no need to declare these as variables of type AtomicBoolean
    public static volatile boolean found = false;
    public static volatile boolean stop = false;

    public static void main(String[] args) {
        // Define desired semiprime to be factorized (other non-prime positive integers could
        // technically also be factorized, but only to their first found two factors)
        final BigInteger n = new BigInteger("1127451830576035879");

        if (n.compareTo(new BigInteger("1")) <= 0) {
            System.out.println("Invalid input!");
            System.exit(1);
        }

        BigInteger result = multiThreadFactor(n);

        if (result == null) {
            System.out.println("The specified integer is a prime, not a semiprime!");
            System.exit(1);
        }

        BigInteger otherFactor = n.divide(result);

        System.out.println("Two factors of " + n + " are: " + result + " and " + otherFactor + ".");
    }

    public static BigInteger multiThreadFactor(BigInteger input) {
        // Create array of factorizers
        final FactorizerWithInterrupt[] factors = new FactorizerWithInterrupt[NUMBER_OF_THREADS];

        // Create array of threads
        final Thread[] factorThreads = new Thread[NUMBER_OF_THREADS];

        // Assign values to each thread and start them
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            // We start from 2 (since 2 is the smallest prime integer)
            factors[i] = new FactorizerWithInterrupt(input, i + 2, NUMBER_OF_THREADS);
            factorThreads[i] = new Thread(factors[i]);
            System.out.println("Starting thread " + (i + 1) + "...");
            factorThreads[i].start();
        }

        while (!found && !stop) {
            // Busy waits and do nothing while waiting for other threads to find the answer/solution
        }

        BigInteger result = null;

        // Wait for any thread to finish and print the result
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            if (factors[i].getResult() != null) {
                result = factors[i].getResult();
            } else {
                System.out.println("Interrupting thread " + (i + 1) + "...");
                factorThreads[i].interrupt();
            }
        }

        return result;
    }
}


// Precondition: n is a semi-prime number.
// Postcondition: the result is a prime factor of n.
class FactorizerWithInterrupt implements Runnable {
    private BigInteger n, start, step, result;

    public FactorizerWithInterrupt(final BigInteger n, final int start, final int step) {
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
            if (Thread.currentThread().isInterrupted())
                break;

            if (this.n.remainder(this.start).compareTo(zero) == 0) {
                this.result = this.start;
                System.out.println("A factor is found!");
                FactorThread.found = true;
                break;
            }

            this.start = this.start.add(this.step);
        }

        FactorThread.stop = true;
    }
}
