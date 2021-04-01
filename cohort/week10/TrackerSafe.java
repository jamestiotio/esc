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
        tracker.setLocation("somestring", Integer.parseInt(String.valueOf(currentThread().getId())),
                Integer.parseInt(String.valueOf(currentThread().getId())));
    }
}


class Viewer extends Thread {
    TrackerSafe tracker;

    public Viewer(TrackerSafe tra) {
        this.tracker = tra;
    }

    @Override
    public void run() {
        TrackerSafe.MutablePoint loc = tracker.getLocation("somestring");
        loc.x = -1212000;
    }
}


class Test {
    Map<String, TrackerSafe.MutablePoint> locations = new HashMap<>();
    TrackerSafe t = new TrackerSafe(locations);
    Updater u = new Updater(t);
    Viewer v = new Viewer(t);

    u.start();
    v.start();

    u.join();
    v.join();
}


// is this class thread-safe?
public class TrackerSafe {
    private ReentrantLock mutex = new ReentrantLock();
    // @guarded by ???
    private static volatile Map<String, MutablePoint> locations;

    // the reference locations, is it going to be an escape?
    public TrackerSafe(Map<String, MutablePoint> locations) {
        mutex.lock();
        TrackerSafe.locations = locations;
        mutex.unlock();
    }

    // is this an escape?
    public Map<String, MutablePoint> getLocations() {
        mutex.lock();
        Map<String, MutablePoint> map = TrackerSafe.locations;
        mutex.unlock();
        return map;
    }

    // is this an escape?
    public MutablePoint getLocation(String id) {
        mutex.lock();
        MutablePoint loc = locations.get(id);
        mutex.unlock();
        return loc;
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

    // this class is not thread-safe (why?) and keep it unmodified.
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
