/**
 * Question for Cohort Exercise 8
 */
// Basically a naive self-implementation of ConcurrentHashMap
public class StripedMap {
    // Synchronization policy: buckets[n] guarded by locks[n % N_LOCKS]
    private static final int N_LOCKS = 16;
    // Assumes that objects in the buckets are independent
    private final Node[] buckets;
    private final Object[] locks;

    public StripedMap(int numBuckets) {
        buckets = new Node[numBuckets];
        locks = new Object[N_LOCKS];

        for (int i = 0; i < N_LOCKS; i++) {
            locks[i] = new Object();
        }
    }

    public Object put(Object key, Object value) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
            // Go through all nodes for for a bucket
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
        // todo: get the item with the given key in the map
        return null;
    }

    public int size() {
        // todo: count the number of elements in the map
        return 0;
    }

    public void clear() {
        // todo: remove all objects in the map
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
}

