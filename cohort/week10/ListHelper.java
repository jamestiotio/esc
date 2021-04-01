import java.util.ArrayList;
import java.util.Collections;

public class ListHelper<E> {
    // The Collections class exposes a client-side locking API/method (it only synchronizes methods
    // of this list)
    public java.util.List<E> list = Collections.synchronizedList(new ArrayList<E>());

    // Thread-safe
    public boolean putIfAbsent(E x) {
        // Synchronize on list instead of this ListHelper object's own intrinsic lock (hence, we
        // cannot make a method with synchronized keyword)
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent) {
                list.add(x);
            }
            return absent;
        }
    }
}
