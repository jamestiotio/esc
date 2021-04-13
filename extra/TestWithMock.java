import org.jmock.Expectations;
import org.junit.Test;
import org.jmock.junit5.JUnit5Mockery;

public class TestWithMock {
    @Test
    public void testCalculatingMachine() {
        // sudiptac: create the context to mock classes
        JUnit5Mockery context = new JUnit5Mockery();

        // sudiptac: mock the classes that are not implemented yet
        final Printer printer = context.mock(Printer.class);
        final Calculator calculator = context.mock(Calculator.class);

        // sudiptac: provide expectations in the test execution
        // @oneOf: one invocation of the function is expected
        // @never: the invocation must never happen
        // @will(returnValue): will expect the return value specified by the argument
        context.checking(new Expectations() {
            {
                oneOf(calculator).add(1, 2);
                // never (printer).print("result is 3");
                // will(returnValue(3));
                // oneOf(printer).print("result is 3");
            }
        });

        // sudiptac: what follows is the test execution
        CalculatingMachine machine = new CalculatingMachine(printer, calculator);
        machine.processAdd(1, 2);

        // sudiptac: fails the above test execution if any expectation is violated
        context.assertIsSatisfied();
    }
}
