import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheV1 {
    // Iterators returned by ConcurrentHashMap are weakly consistent instead of fail-fast (it also
    // employs lock stripping and does not support client-based locking)
    private final ConcurrentHashMap<Integer, List<Integer>> results =
            new ConcurrentHashMap<Integer, List<Integer>>(); // the factors must be the factors of
                                                             // the corresponding number

    // This is a big problem if factor(a) is required to execute once-and-only-once (no redundant
    // computations).
    public List<Integer> service(int input) {
        List<Integer> factors = results.get(input);

        if (factors == null) {
            factors = factor(input);
            results.put(input, factors);
        }

        return factors;
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
