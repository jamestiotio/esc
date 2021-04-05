package BoundedBuffer;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Question for Cohort Exercise 3.
 */

public class BoundedBufferTest {
    private static final long LOCKUP_DETECT_TIMEOUT = 1000;

    @Test
    public void testIsEmptyWhenConstructued() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    @Test
    public void testIsFullAfterPuts() throws InterruptedException {
        final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);

        Runnable task = new Runnable() {
            public void run() {
                try {
                    bb.put((new Random()).nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }

        assertTrue(bb.isFull());
        assertFalse(bb.isEmpty());
    }

    /**
     * TODO: implement this test Initialize a buffer with #items inside. Initialize multiple threads
     * that concurrently removes item from the buffer. Make sure the total remove() = #items in
     * buffer. What should be the postcondition?
     */
    @Test
    public void testIsEmptyAfterTakesAll() {
    }

    /**
     * TODO: implement this test Initialize a buffer with 0 item inside. Initialize a threads
     * removes item from the buffer. What should be the postcondition?
     */
    @Test
    public void testTakeBlocksWhenEmpty() {

    }
}
