public class VolatileOnly {
    public volatile int inc = 0;

    public void increase() {
        inc++;
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
        System.out.println(test.inc);
    }
}
