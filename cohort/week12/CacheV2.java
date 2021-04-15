import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CacheV2 {
    private final ConcurrentHashMap<Integer, Future<List<Integer>>> results =
            new ConcurrentHashMap<Integer, Future<List<Integer>>>();

    // This can still be executed twice (redundant computation)
    public List<Integer> service(final int input) throws Exception {
        // This checks if anyone is currently still working on it (and if f is not null, wait until
        // someone finished the task)
        Future<List<Integer>> f = results.get(input);

        if (f == null) {
            // Setup the callable, put the future task into cache and start solving.
            Callable<List<Integer>> eval = new Callable<List<Integer>>() {
                public List<Integer> call() throws InterruptedException {
                    return factor(input);
                }
            };

            FutureTask<List<Integer>> ft = new FutureTask<List<Integer>>(eval);
            f = ft;
            results.put(input, ft); // Telling others that still working on it now (not necessarily
                                    // have finished the job and putting the result; only announcing
                                    // about it).
            ft.run(); // I'm really working on it and actually doing the work.
        }

        return f.get(); // Someone is working on it and they will get it done eventually.
    }

    public List<Integer> factor(int n) {
        List<Integer> factors = new ArrayList<Integer>();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }

        return factors;
    }
}
