import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Solution for Cohort Exercise 5
 */
public class SPMDExerciseSolution {
    static double pi = 0;
    static final Object lock = new Object();

    public static void main(String[] args) throws Exception {
        int NTHREADS = 5;
        ExecutorService exec = Executors.newFixedThreadPool(NTHREADS - 1);

        final double stepSize = 1.0 / NTHREADS;
        for (int i = 0; i < NTHREADS; i++) {
            final double a = i * stepSize;
            final double b = (i + 1) * stepSize;

            /*
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    synchronized (lock) {
                        SPMDExerciseSolution.pi += integrate(a, b);
                    }
                }
            });
            */

            Future<Double> result = exec.submit(new Callable<Double>() {
                public Double call() throws Exception {
                    return integrate(a, b);
                }
            });

            SPMDExerciseSolution.pi += result.get();
        }

        System.out.println(SPMDExerciseSolution.pi);
        exec.shutdown();
    }

    public static double f(double x) {
        return 4.0 / (1 + x * x);
    }

    // This is the sequential program which does numerical integration using Trapezoidal rule.
    public static double integrate(double a, double b) {
        int N = 10000; // preciseness parameter
        double h = (b - a) / (N - 1); // step size

        // 1/2 terms
        double sum = 1.0 / 2.0 * (f(a) + f(b));

        for (int i = 1; i < N - 1; i++) {
            double x = a + h * i;
            sum += f(x);
        }

        return sum * h;
    }
}
