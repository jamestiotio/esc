import java.math.BigInteger;

public class FactorThread {
    // Define desired thread count/number of threads (time complexity: O(n/k), where k is the number
    // of threads)
    public static final int numberOfThreads = 8;
    // There is no need to declare this as a variable of type AtomicBoolean
    public static boolean found = false;

    public static void main(String[] args) {
        // Define desired semiprime to be factorized
        final BigInteger n = new BigInteger("4294967297");

        BigInteger result = multiThreadFactor(n);
        BigInteger otherFactor = n.divide(result);

        System.out.println("The two prime factors of the semiprime " + n + " are: " + result
                + " and " + otherFactor + ".");
    }

    public static BigInteger multiThreadFactor(BigInteger input) {
        // Create array of factorizers
        final FactorizerWithInterrupt[] factors = new FactorizerWithInterrupt[numberOfThreads];

        // Create array of threads
        final Thread[] factorThreads = new Thread[numberOfThreads];

        // Assign values to each thread and start them
        for (int i = 0; i < numberOfThreads; i++) {
            // We start from 2 (since 2 is the smallest prime integer)
            factors[i] = new FactorizerWithInterrupt(input, i + 2, numberOfThreads);
            factorThreads[i] = new Thread(factors[i]);
            factorThreads[i].start();
        }

        while (!found) {
            // Busy waits and do nothing while waiting for other threads to find the answer/solution
        }

        BigInteger result = null;

        // Wait for any thread to finish and print the result
        for (int i = 0; i < numberOfThreads; i++) {
            if (factors[i].getResult() != null) {
                result = factors[i].getResult();
            } else {
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
                FactorThread.found = true;
                break;
            }

            this.start = this.start.add(this.step);
        }

        // This should never be reached as it implies that an error occurs
        assert (false);
    }
}
