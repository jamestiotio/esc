import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StackTest {
    private Stack<Integer> stack;
    // setUp method using @Before syntax
    // @Before methods are run before each test
    @Before 
    public void runBeforeEachTest()
    {
        System.out.println("setting up");
        stack = new Stack<Integer>();
    }

    // tear-down method using @After
    // @After methods are run after each test
    @After 
    public void runAfterEachTest()
    {
        stack = null;
        System.out.println("setting down");
    }

    @Test public void testToString()
    {
        System.out.println("testing");
        stack.push(Integer.valueOf(1));
        stack.push(Integer.valueOf(2));
        assertEquals ("{2, 1}", stack.toString());
    }

    @Test public void testEmptyStack()
    {
        System.out.println("testing");
        boolean result = stack.repOK();
        // Check representation of empty stack
        assertEquals(true, result);
    }

    @Test public void testOnePush() {
        stack.push(Integer.valueOf(1));
        boolean result = stack.repOK();
        // Check representation of stack after one push
        assertEquals(true, result);
    }

    @Test public void testOnePushAndOnePop() {
        stack.push(Integer.valueOf(1));
        stack.pop();
        boolean result = stack.repOK();
        // Check representation of stack after one push and one pop
        assertEquals(true, result);
    }

    @Test public void testAlternatingPushesAndPops() {
        stack.push(Integer.valueOf(1));
        stack.pop();
        stack.push(Integer.valueOf(1));
        stack.pop();
        boolean result = stack.repOK();
        // Check representation of stack after alternating pushes and pops (two each in total)
        assertEquals(true, result);
    }
}