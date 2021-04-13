import java.util.concurrent.atomic.AtomicInteger;

public class Range {
    // use of atomic integer may not be necessary, and it's slower to create than int
    // public final AtomicInteger lower = new AtomicInteger(0);
    // public final AtomicInteger upper = new AtomicInteger(0);

    // @invariant: lower <= upper
    public int lower = 0;
    public int upper = 10;

    // Fix: use synchronized lock to make sure upper and lower are not being set at the same time
    // @guarded by 'this'
    public synchronized void setLower(int i) {
        if (i > upper) {
            throw new IllegalArgumentException("Can't set lower to " + i + " > upper");
        }

        lower = i;
    }

    // @guarded by 'this'
    public synchronized void setUpper(int i) {
        if (i < lower) {
            throw new IllegalArgumentException("Can't set upper to " + i + " < lower");
        }

        upper = i;
    }

    // isInRange may be considered thread safe, if our specification considers it
    // acceptable to have a different isInRange result if the upper or lower is changed
    // while the test is carried out
    public boolean isInRange(int i) {
        return (i >= lower) && (i <= upper);
    }
}
