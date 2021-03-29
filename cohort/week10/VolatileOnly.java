import java.util.concurrent.atomic.AtomicInteger;

public class VolatileOnly {
    public volatile AtomicInteger inc = new AtomicInteger(0);

    public void increase() {
        inc.incrementAndGet();
    }

    public static void main(String[] args) {
        final VolatileOnly test = new VolatileOnly();
        int numThreads = 10;
        int initialCount = Thread.activeCount();
        for (int i = 0; i < numThreads; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++)
                    test.increase();
            }).start();
        }
        while (Thread.activeCount() > initialCount)
            Thread.yield();
        // Final result should be 1000 * 10 = 10000 (modification is visible to everyone but race
        // condition still happens if we only use a normal int instead of AtomicInteger).
        // Lock ensures memory visibility and thus guarantees synchronization.
        System.out.println(test.inc.intValue());
    }
}
