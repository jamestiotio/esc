import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

class Updater extends Thread {
    TrackerSafe tracker;

    public Updater(TrackerSafe tra) {
        this.tracker = tra;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            int x = Integer.parseInt(String.valueOf(currentThread().getId()));
            int y = Integer.parseInt(String.valueOf(currentThread().getId()));
            System.out.println(
                    "Updating the coordinates of location " + i + " to (" + x + ", " + y + ")...");
            tracker.setLocation(String.valueOf(i), x, y);
        }
    }
}


class Viewer extends Thread {
    TrackerSafe tracker;

    public Viewer(TrackerSafe tra) {
        this.tracker = tra;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            TrackerSafe.MutablePoint loc = tracker.getLocation(String.valueOf(i));
            System.out.println(
                    "Coordinates of location " + i + " are: (" + loc.x + ", " + loc.y + ")");
            loc.x = -1212000;
        }
    }
}


class Test {
    public static void main(String[] args) throws InterruptedException {
        Map<String, TrackerSafe.MutablePoint> locations = new HashMap<>();
        TrackerSafe t = new TrackerSafe(locations);
        t.addRandomLocations();
        Updater u = new Updater(t);
        Viewer v = new Viewer(t);

        u.start();
        v.start();

        u.join();
        v.join();

        // Everything should have the same coordinates now
        Viewer v2 = new Viewer(t);
        v2.start();
        v2.join();
    }
}


// Is this class thread-safe?
public class TrackerSafe {
    // ReentrantLock is more unstructured and has more features
    private ReentrantLock mutex = new ReentrantLock();
    // @guarded by "this"
    private static volatile Map<String, MutablePoint> locations;

    // Helper function to do a deep copy to ensure instance confinement
    private final Map<String, MutablePoint> deepcopy(final Map<String, MutablePoint> input) {
        Map<String, MutablePoint> copy = new HashMap<String, MutablePoint>();
        for (String x : input.keySet()) {
            MutablePoint newPoint = new MutablePoint(input.get(x));
            copy.put(x, newPoint);
        }
        return copy;
    }

    // The reference locations, is it going to be an escape?
    // Yes, a reference to the locations object was created and assigned.
    // Hence, the locations object was modifiable.
    // Assign a deep copy of the locations object instead.
    public TrackerSafe(Map<String, MutablePoint> locations) {
        mutex.lock();
        TrackerSafe.locations = deepcopy(locations);
        mutex.unlock();
    }

    public void addRandomLocations() {
        for (int i = 0; i < 100; i++) {
            int x = new SecureRandom().nextInt(100);
            int y = new SecureRandom().nextInt(100);
            TrackerSafe.locations.put(String.valueOf(i), new MutablePoint(x, y));
        }
    }

    // Is this an escape?
    // Yes, a reference to the locations object was created and returned.
    // This is because the Map object is a non-primitive data type (hence, pass-by-reference).
    // It is different from the normal usual pass-by-value behavior done by Java for primitive data types.
    // Hence, the locations object was modifiable.
    // Return a deep copy of the locations object instead.
    public Map<String, MutablePoint> getLocations() {
        mutex.lock();
        Map<String, MutablePoint> map = deepcopy(TrackerSafe.locations);
        mutex.unlock();
        return map;
    }

    // Is this an escape?
    // Yes, since a reference to the loc MutablePoint object was returned.
    // This is because a Class is not a primitive data type (hence, pass-by-reference).
    // It is different from the normal usual pass-by-value behavior done by Java for primitive data types.
    // Hence, the attributes of the loc object was modifiable.
    // Instead, we return a new instance of MutablePoint and synchronize using ReentrantLock so that
    // the locations map will be visible.
    public MutablePoint getLocation(String id) {
        mutex.lock();
        MutablePoint loc = locations.get(id);
        MutablePoint point = null;
        if (loc != null) {
            point = new MutablePoint(loc.x, loc.y);
        }
        mutex.unlock();
        return point;
    }

    public void setLocation(String id, int x, int y) {
        mutex.lock();
        MutablePoint loc = locations.get(id);

        if (loc == null) {
            throw new IllegalArgumentException("No such ID: " + id);
        }

        loc.x = x;
        loc.y = y;
        mutex.unlock();
    }

    // This class is not thread-safe (why?) and keep it unmodified.
    // The x and y attributes are public, and thus they can be modified at any time by anyone.
    class MutablePoint {
        public int x, y;

        public MutablePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public MutablePoint(MutablePoint p) {
            this.x = p.x;
            this.y = p.y;
        }
    }
}
