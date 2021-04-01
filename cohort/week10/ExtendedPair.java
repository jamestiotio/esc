public class ExtendedPair extends Pair {
    public ExtendedPair(int x, int y) {
        super(x, y);
    }

    // This is correct extension since the Pair class uses the synchronized keyword (i.e.,
    // synchronized on the same object)
    public synchronized void incrementXY() {
        incrementX();
        incrementY();
    }
}


class Pair {
    private int x;
    private int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public synchronized void incrementX() {
        x++;
    }

    public synchronized void incrementY() {
        y++;
    }
}
