import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DiskTest {
    private static final int MIN_TIMEOUT = 5000;

    // Expect this test to be still running after 5 seconds
    @Test
    public void testNonTerminating() throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Disk disk = new Disk(1001, -10);
                disk.manipulate();
            }
        };

        thread.start();

        // Let the current thread sleep (not the created thread!)
        Thread.sleep(MIN_TIMEOUT);

        assertTrue(thread.isAlive());
    }
}