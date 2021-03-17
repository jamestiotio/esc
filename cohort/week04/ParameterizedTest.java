import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;
import java.util.*;

// @sudiptac: Include this for running parameterized tests
@RunWith(Parameterized.class)
public class ParameterizedTest {
    public int expectedSum, a, b;

    // Classic constructor
    public ParameterizedTest(int sum, int a, int b) {
        this.expectedSum = sum;
        this.a = a;
        this.b = b;
    }

    // @sudiptac: Decide the list of parameters to be sent to the class
    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][] {{0, 0, 0}, {2, 1, 1}, {3, 2, 1}});
    }

    // @sudiptac: This test is invoked for each of the parameter sent via parameters()
    @Test
    public void additionTest() {
        assertEquals(expectedSum, Sum.addition(a, b));

        // Do parameterized tests instead of manually writing all of the individual tests
        // assertEquals(0, Sum.addition(0, 0));
        // assertEquals(2, Sum.addition(1, 1));
        // assertEquals(3, Sum.addition(2, 1));
    }
}
