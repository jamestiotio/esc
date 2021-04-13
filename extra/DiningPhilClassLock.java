import java.util.Random;

public class DiningPhilClassLock {
    /*
     * implement class lock around the critical section only 1 philosopher is able to pick up both
     * forks and eat others must wait until that philosopher puts down both forks
     */
    private static int N = 2;

    public static void main(String[] args) throws Exception {
        Philosopher[] phils = new Philosopher[N];
        Fork[] forks = new Fork[N];

        for (int i = 0; i < N; i++) {
            forks[i] = new Fork(i);
        }

        for (int i = 0; i < N; i++) {
            phils[i] = new Philosopher(i, forks[(i + N - 1) % N], forks[i]);
            System.out.println((i + N - 1) % N);
            System.out.println(i);
            phils[i].start();
        }
    }
}


class Philosopher extends Thread {
    private final int index;
    private final Fork left;
    private final Fork right;

    public Philosopher(int index, Fork left, Fork right) {
        this.index = index;
        this.left = left;
        this.right = right;
    }

    public void run() {
        Random randomGenerator = new Random();
        try {
            while (true) {
                Thread.sleep(randomGenerator.nextInt(100)); // not sleeping but thinking
                System.out.println("Phil " + index + " finishes thinking.");
                synchronized (Philosopher.class) {
                    left.pickup();
                    System.out.println("Phil " + index + " picks up left fork.");
                    right.pickup();
                    System.out.println("Phil " + index + " picks up right fork.");
                    Thread.sleep(randomGenerator.nextInt(100)); // eating
                    System.out.println("Phil " + index + " finishes eating.");
                    left.putdown();
                    System.out.println("Phil " + index + " puts down left fork.");
                    right.putdown();
                    System.out.println("Phil " + index + " puts down right fork.");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Don't disturb me while I am sleeping, well, thinking.");
        }
    }
}


class Fork {
    private final int index;
    private boolean isAvailable = true;

    public Fork(int index) {
        this.index = index;
    }

    public synchronized void pickup() throws InterruptedException {
        while (!isAvailable) {
            wait();
        }

        isAvailable = false;
        notifyAll();
    }

    public synchronized void putdown() throws InterruptedException {
        while (isAvailable) {
            wait();
        }

        isAvailable = true;
        notifyAll();
    }

    public String toString() {
        if (isAvailable) {
            return "Fork " + index + " is available.";
        } else {
            return "Fork " + index + " is NOT available.";
        }
    }
}
