import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {
    private final Map<Integer, List<Integer>> results = new HashMap<Integer, List<Integer>>();

    public static void main(String[] args) {
        // Thread[] woker = new Thread[];
    }

    // Synchronized keyword here leads to poor concurrency (is the cache useful here?)
    public synchronized List<Integer> service(int input) {
        List<Integer> factors = results.get(input);

        if (factors == null) {// haven't got the factors for the given input yet.
            factors = factor(input);// identify the factors.
            results.put(input, factors);// put the factors into results.
        }

        return factors;
    }

    /**
     * identify all factors of a given number.
     *
     * @param n
     * @return
     */
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
