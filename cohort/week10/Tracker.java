import java.util.HashMap;
import java.util.Map;

class Updater extends Thread {
    Tracker tracker;

    public Updater(Tracker tra) {
        this.tracker = tra;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            tracker.setLocation(String.valueOf(i),
                    Integer.parseInt(String.valueOf(currentThread().getId())),
                    Integer.parseInt(String.valueOf(currentThread().getId())));
        }
    }
}


class Viewer extends Thread {
    Tracker tracker;

    public Viewer(Tracker tra) {
        this.tracker = tra;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            Tracker.MutablePoint loc = tracker.getLocation(String.valueOf(i));
            loc.x = -1212000;
        }
    }
}


class Test {
    public static void main(String[] args) throws InterruptedException {
        Map<String, Tracker.MutablePoint> locations = new HashMap<>();
        Tracker t = new Tracker(locations);
        Updater u = new Updater(t);
        Viewer v = new Viewer(t);

        u.start();
        v.start();

        u.join();
        v.join();
    }
}


// is this class thread-safe?
public class Tracker {
    // @guarded by ???
    private final Map<String, MutablePoint> locations;

    // the reference locations, is it going to be an escape?
    public Tracker(Map<String, MutablePoint> locations) {
        this.locations = locations;
    }

    // is this an escape?
    public Map<String, MutablePoint> getLocations() {
        return locations;
    }

    // is this an escape?
    public MutablePoint getLocation(String id) {
        MutablePoint loc = locations.get(id);
        return loc;
    }

    public void setLocation(String id, int x, int y) {
        MutablePoint loc = locations.get(id);

        if (loc == null) {
            throw new IllegalArgumentException("No such ID: " + id);
        }

        loc.x = x;
        loc.y = y;
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
