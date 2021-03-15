import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class DiskParameterizedTest {
    public int x, y;

    public DiskParameterizedTest(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Do take note that the second set of input values will cause the test to not terminate
    @Parameterized.Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][] {{4, 11}, {1001, -2}, {4, 13}});
    }

    @Test
    public void manipulateTest() {
        Disk disk = new Disk(x, y);
        disk.manipulate();
    }
}
