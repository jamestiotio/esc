import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

public class StripedMapTest {
    private StripedMapSolution map;
    private final int LOOP_NUM = 10000;
    private final int NUM_OF_THREADS = 100;

    @Before
    public void setup() {
        map = new StripedMapSolution(64);

        // First task
        Runnable firstTask = new Runnable() {
            public void run() {
                map.put("firstKey", 100);
            }
        };

        // Second task
        Runnable secondTask = new Runnable() {
            public void run() {
                map.put("firstKey", 100);
            }
        };

        // Third task
        Runnable thirdTask = new Runnable() {
            public void run() {
                map.put("secondKey", 75);
            }
        };

        // Fourth task
        Runnable fourthTask = new Runnable() {
            public void run() {
                map.put("secondKey", 75);
            }
        };

        // Fifth task
        Runnable fifthTask = new Runnable() {
            public void run() {
                map.put("thirdKey", 69);
            }
        };

        ExecutorService exec = Executors.newFixedThreadPool(NUM_OF_THREADS);
        for (int i = 0; i < LOOP_NUM; i++) {
            exec.submit(firstTask);
            exec.submit(secondTask);
            exec.submit(thirdTask);
            exec.submit(fourthTask);
            exec.submit(fifthTask);
        }

        exec.shutdown();
        try {
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println("Unexpected behavior has occurred!");
        }
    }

    @Test
    public void testGet() {
        Object firstVal = map.get("firstKey");
        Object secondVal = map.get("secondKey");
        Object thirdVal = map.get("thirdKey");
        Object nullVal = map.get("noSuchKey");
        assert ((int) firstVal == 100);
        assert ((int) secondVal == 75);
        assert ((int) thirdVal == 69);
        assert (nullVal == null);
    }

    @Test
    public void testSize() {
        assert (map.size() == 3);
    }

    @Test
    public void testClear() {
        map.clear();
        assert (map.size() == 0);
    }
}
