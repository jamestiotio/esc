import java.util.concurrent.atomic.AtomicInteger;

/**
 * Question for Cohort Exercise 8
 */
// Basically a naive self-implementation of ConcurrentHashMap.
public class StripedMapSolution {
    // Synchronization policy: buckets[n] guarded by locks[n % N_LOCKS].
    private static final int N_LOCKS = 16;
    // Assumes that objects in the buckets are independent.
    private final Node[] buckets;
    private final Object[] locks;

    public StripedMapSolution(int numBuckets) {
        buckets = new Node[numBuckets];
        locks = new Object[N_LOCKS];

        for (int i = 0; i < N_LOCKS; i++) {
            locks[i] = new Object();
        }
    }

    public Object put(Object key, Object value) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
            // Go through all nodes for for a bucket.
            for (Node m = buckets[hash]; m != null; m = m.next)
                if (m.key.equals(key)) {
                    m.value = value;
                    return m.value;
                }
            buckets[hash] = new Node(key, value, buckets[hash]);
        }
        return null;
    }

    public Object get(Object key) {
        int hash = hash(key);

        // TODO: Get the item with the given key in the map.
        // Get method only needs to acquire one lock.
        synchronized (locks[hash % N_LOCKS]) {
            for (Node m = buckets[hash]; m != null; m = m.next)
                if (m.key.equals(key)) {
                    return m.value;
                }
        }

        return null;
    }

    public int size() {
        AtomicInteger size = new AtomicInteger(0);

        // TODO: Count the number of elements in the map.
        // Size method needs to acquire all locks, but not necessarily simultaneously.
        for (int i = 0; i < buckets.length; i++) {
            synchronized (locks[i % N_LOCKS]) {
                // Count length of bucket.
                for (Node m = buckets[i]; m != null; m = m.next) {
                    size.getAndIncrement();
                }
            }
        }

        return size.intValue();
    }

    public void clear() {
        // TODO: Remove all objects in the map.
        // Clear method needs to acquire all locks, but not necessarily simultaneously.
        for (int i = 0; i < buckets.length; i++) {
            // Takes lock so nobody reads outdated value or updates the buckets.
            // However, when you delete buckets 0 to 15, somebody can read buckets 16 to 31.
            synchronized (locks[i % N_LOCKS]) {
                buckets[i] = null;
            }
        }
    }

    private final int hash(Object key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    class Node {
        Node next;
        Object key;
        Object value;

        Node(Object key, Object value, Node next) {
            this.next = next;
            this.key = key;
            this.value = value;
        }
    }

    // For testing and debugging purposes
    public static void main(String[] args) {
        StripedMapSolution s = new StripedMapSolution(128);
        s.put(100, 1000);
        System.out.println(s.get(100));
        System.out.println(s.get(101));
        System.out.println(s.size());
        s.clear();
        System.out.println(s.size());
    }
}
