import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FindMaxTest {
    // Expect this test to pass
    @Test
    public void testPass() {
        int result = FindMax.max(new int[] {10, 2, 3, 0, -1});
        assertEquals(10, result);
    }

    // Expect this test to error
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testError() {
        int result = FindMax.max(new int[] {});
    }

    // Expect this test to fail
    @Test(expected = AssertionError.class)
    public void testFailure() {
        // The buggy implementation of FindMax would not be able to find the maximum if it is in the
        // end of the array
        int result = FindMax.max(new int[] {2, 3, 0, -1, 10});
        assertEquals(10, result);
    }
}
