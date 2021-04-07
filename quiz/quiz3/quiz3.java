import java.util.Random;
import java.security.SecureRandom;

public class quiz3 {
    static int n = 1000;
    static int sum = 0;
    static int[] inputs = new int[n];
    final static int k = 16; // Number of threads
    static double mean, median;
    static int[] sortedInputs;

    // Slightly modified implementation from original question (only add non-negative integers)
    public static void populateInput() {
        for (int i = 0; i < n; i++) {
            inputs[i] = new SecureRandom().nextInt(n);
        }
    }

    // This is only for single-threaded summation
    public static void computeSum() {
        for (int i = 0; i < n; i++) {
            sum += inputs[i];
        }
    }

    // Extra stuff
    public static void computeMean() {
        mean = (double) sum / (double) n;
    }

    public static void main(String[] args) {
        populateInput();
        // computeSum();

        // Execute multi-threaded summation
        final Summer[] partialSums = new Summer[k];
        final Thread[] sumThreads = new Thread[k];

        // Start the threads to partially sum the inputs
        for (int i = 0; i < k; i++) {
            partialSums[i] = new Summer(inputs, i, k);
            sumThreads[i] = new Thread(partialSums[i]);
            System.out.println("Starting thread " + (i + 1) + "...");
            sumThreads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < k; i++) {
            try {
                sumThreads[i].join();
                System.out.println(
                        "Thread " + (i + 1) + " has finished calculating its partial sum.");
            } catch (InterruptedException e) {
                System.out.println("Thread " + (i + 1) + " was interrupted.");
                e.printStackTrace();
            }
        }

        for (int i = 0; i < k; i++) {
            sum += partialSums[i].getResult();
        }

        // Extra stuff
        computeMean();

        // Iteratively merge sort the input array in a multi-threaded fashion
        // Do not cheat by using Arrays.sort() instead! (Defeats the point of the question)

        // Execute multi-threaded sorting
        BottomUpMergeSort sorter = new BottomUpMergeSort(inputs, k);
        Thread mainSorterThread = new Thread(sorter);
        mainSorterThread.start();

        try {
            mainSorterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sortedInputs = sorter.getResult();

        // Get the median and assign it to a variable
        if (n % 2 == 0) {
            median = ((double) sortedInputs[n / 2] + (double) sortedInputs[n / 2 - 1]) / 2;
        } else {
            median = (double) sortedInputs[n / 2];
        }

        System.out.println("The sum is: " + sum);
        System.out.println("The mean is: " + mean);
        System.out.println("The median is: " + median);
    }
}


// Sum all the randomly-generated non-negative integers in the input array
class Summer implements Runnable {
    private int[] inputs;
    private int start, step, result;

    public Summer(int[] inputs, int start, int step) {
        this.inputs = inputs;
        this.start = start;
        this.step = step;
        this.result = 0;
    }

    public int getResult() {
        return this.result;
    }

    public void run() {
        this.computeSum();
    }

    private void computeSum() {
        for (int i = this.start; i < this.inputs.length; i += this.step) {
            this.result += this.inputs[i];
        }
    }
}


// This code is adapted from the original code written by @CarrCodes
// (https://gist.github.com/CarrCodes/1174f1ab0f75bb4789d043e907b7710b)
class BottomUpMergeSort implements Runnable {
    private int[] result;
    private int numberOfThreads;

    public BottomUpMergeSort(int[] inputs, int numberOfThreads) {
        this.result = inputs.clone(); // We can do this since int is primitive (deep copy is not
                                      // needed/required)
        this.numberOfThreads = numberOfThreads;
    }

    public int[] getResult() {
        return this.result;
    }

    public void run() {
        this.parallelMergeSort(this.result, 0, this.result.length - 1, this.numberOfThreads);
    }

    private void parallelMergeSort(int[] result, int from, int to, int availableThreads) {
        if (from < to) {
            if (availableThreads <= 1) {
                this.mergeSort(result, from, to);
            } else {
                int middle = to / 2;

                Thread firstHalf = new Thread(new Runnable() {
                    public void run() {
                        parallelMergeSort(result, from, middle, availableThreads - 1);
                    }
                });
                Thread secondHalf = new Thread(new Runnable() {
                    public void run() {
                        parallelMergeSort(result, middle + 1, to, availableThreads - 1);
                    }
                });

                firstHalf.start();
                secondHalf.start();

                try {
                    firstHalf.join();
                    secondHalf.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.merge(result, from, middle, to);
            }
        }
    }

    private void mergeSort(int[] result, int from, int to) {
        if (from == to)
            return;
        if (from < to) {
            int mid = (from + to) / 2;

            // Sort the first and second half
            this.mergeSort(result, from, mid);
            this.mergeSort(result, mid + 1, to);
            this.merge(result, from, mid, to);
        }
    }

    private void merge(int[] result, int from, int mid, int to) {
        // Size of the array range to be merged
        int n = to - from + 1;

        // Merge both halves into a temporary array x
        int[] x = new int[n];

        int a = from; // Next element to consider in the first range
        int b = mid + 1; // Next element to consider in the second range
        int j = 0; // Next open position in x

        // As long as neither a or b go past beyond the end, move the smaller element into x
        while (a <= mid && b <= to) {
            if (result[a] < result[b]) {
                x[j] = result[a];
                a++;
            } else {
                x[j] = result[b];
                b++;
            }
            j++;
        }

        // Note that only one of the two while loops below will be executed

        // Copy any remaining entries of the first half
        while (a <= mid) {
            x[j] = result[a];
            a++;
            j++;
        }

        // Copy any remaining entries of the second half
        while (b <= to) {
            x[j] = result[b];
            b++;
            j++;
        }

        // Copy back from the temporary array
        for (int m = 0; m < n; m++) {
            result[from + m] = x[m];
        }
    }
}
