import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RussianTest {
    @Test
    public void blackBoxTest1() {
        assertEquals(54, Russian.multiply(6, 9));
    }

    @Test
    public void blackBoxTest2() {
        assertEquals(0, Russian.multiply(0, 0));
    }

    @Test
    public void blackBoxTest3() {
        assertEquals(100000000, Russian.multiply(10000, 10000));
    }

    // This test will fail (cannot handle a negative value of n)
    @Test
    public void blackBoxTest4() {
        assertEquals(100000000, Russian.multiply(-10000, -10000));
    }

    @Test
    public void blackBoxTest5() {
        assertEquals(-100000000, Russian.multiply(-10000, 10000));
    }

    // This test will fail (cannot handle a negative value of n)
    @Test
    public void blackBoxTest6() {
        assertEquals(-100000000, Russian.multiply(10000, -10000));
    }

    @Test
    public void whiteBoxTest1() {
        assertEquals(0, Russian.multiply(420, 0));
    }

    // This test will fail (cannot handle a negative value of n)
    @Test
    public void whiteBoxTest2() {
        assertEquals(-840, Russian.multiply(420, -2));
    }

    @Test
    public void whiteBoxTest3() {
        assertEquals(15, Russian.multiply(3, 5));
    }

    @Test
    public void whiteBoxTest4() {
        assertEquals(18, Russian.multiply(3, 6));
    }

    @Test
    public void whiteBoxTest5() {
        assertEquals(35, Russian.multiply(5, 7));
    }

    @Test
    public void whiteBoxTest6() {
        assertEquals(1414416, Russian.multiply(237, 5968));
    }

    // This test will fail (cannot handle a negative value of n)
    @Test
    public void faultBasedTest1() {
        assertEquals(-420, Russian.multiply(420, -1));
    }

    // This test will fail (integer overflow)
    @Test
    public void faultBasedTest2() {
        assertEquals((int) (Math.pow(Integer.MAX_VALUE, 2)),
                Russian.multiply(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    // This test will fail (cannot handle a negative value of n)
    @Test
    public void faultBasedTest3() {
        assertEquals((int) (Math.pow(Integer.MIN_VALUE, 2)),
                Russian.multiply(Integer.MIN_VALUE, Integer.MIN_VALUE));
    }
}
