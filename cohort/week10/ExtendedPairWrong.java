public class ExtendedPairWrong extends NewPair {
    public ExtendedPairWrong(int x, int y) {
        super(x, y);
    }

    // This is wrong since it is locked on different objects (class extension is fragile since it
    // depends on the other class' specific implementation and access to its source code)
    public synchronized void increment() {
        incrementX();
        incrementY();
    }
}


class NewPair {
    private int x;
    private int y;
    private Object lockx = new Object();
    private Object locky = new Object();


    public NewPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void incrementX() {
        synchronized (lockx) {
            x++;
        }
    }

    public void incrementY() {
        synchronized (locky) {
            y++;
        }
    }
}
