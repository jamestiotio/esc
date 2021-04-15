// CAS is supported in atomic variable classes (java.util.concurrent.atomic). In reality, it is
// provided by a very fast, single-core CPU single processor-level instruction.
public class SimulatedCAS {
    private int value;

    public synchronized int get() {
        return value;
    }

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue)
            value = newValue;
        return oldValue;
    }

    // Check for a successful compareAndSwap()
    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return (expectedValue == compareAndSwap(expectedValue, newValue));
    }
}
