import java.math.BigInteger;
import java.util.Arrays;

public class FactorThreadNoInterrupt {
    // Define desired thread count/number of threads (time complexity: O(n/k), where k is the number of threads)
    public static final int threadCount = 8;

    public static void main(String[] args) {
        BigInteger[] result = new BigInteger[threadCount];

        // Define desired semiprime to be factorized
        final BigInteger n = new BigInteger("4294967297");

        // Create array of factorizers
        final Factorizer[] factorizers = new Factorizer[threadCount];

        // Create array of threads
        final Thread[] factorThreads = new Thread[threadCount];

        // Assign values to each thread and start them
        for (int i = 0; i < threadCount; i++) {
            // We start from 2 (since 2 is the smallest prime integer)
            factorizers[i] = new Factorizer(n, i + 2, threadCount);
            factorThreads[i] = new Thread(factorizers[i]);
            factorThreads[i].start();
        }

        // Wait for any thread to finish and print the result
        for (int i = 0; i < threadCount; i++) {
            try {
                factorThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            result[i] = factorizers[i].getResult();
            if (result[i] != null) {
                System.out.println(result[i] + " is a prime factor of " + n.toString() + ".");
            }
        }

        if (Arrays.stream(result).allMatch(val -> val == null)) {
            System.out.println("The specified integer is a prime, not a semiprime!");
        }
    }
}

// Precondition: n is a semi-prime number.
// Postcondition: the result is a prime factor of n.
class Factorizer implements Runnable {
    private BigInteger n, start, step, result;

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
            if (this.n.remainder(this.start).compareTo(zero) == 0) {
                this.result = this.start;
                break;
            }

            this.start = this.start.add(this.step);
        }

        // This should never be reached as it implies that an error occurs
        assert(false);
    }
}