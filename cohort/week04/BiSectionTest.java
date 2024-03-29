import org.junit.Before;
import org.junit.Test;

// This test suite obtains 100% method coverage
public class BiSectionTest {
    private BiSectionExample bi;

    @Before
    public void runBeforeEachTest() {
        bi = new BiSectionExample();
    }

    @Test
    public void test4MethodCoverage() {
        assert (bi.root(0.5, 100.3, 0.1) >= 100);
        // Question: Should we assert the returned value is the exact value we expect?
    }

    @Test
    public void test4LoopCoverage1() { // Loop once
        assert (bi.root(0, 100, 80) > 50);
    }
}
