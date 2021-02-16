import static org.junit.Assert.*;

import org.junit.Test;

public class BadCodeTest {
    @Test
    public void testDiv() {
        BadCode crash = new BadCode();

        try {
            crash.div(2,0);
            //sudiptac: you catch bad coder like this
            fail("I got you :)");
        } catch (Exception e) {
            System.out.println("It is a GOOD Idea");
        }
    }
}