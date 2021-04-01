import java.util.Vector;

// The Vector class is synchronized based on the official JDK documentation (need to follow the same
// locking policy as superclass)
public class BetterVector<E> extends Vector<E> {
    // The synchronized keyword is the same as synchronizing on self, so must/should lock on the
    // same exact lock (synchronized (this) {...})
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !contains(x);

        if (absent) {
            add(x);
        }

        return absent;
    }
}
