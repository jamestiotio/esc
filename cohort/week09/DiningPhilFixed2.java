import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

// Use a timed tryLock
public class DiningPhilFixed2 {
    private static int N = 5;

    public static void main(String[] args) throws Exception {
        Philosopher2[] phils = new Philosopher2[N];
        Fork2[] forks = new Fork2[N];

        for (int i = 0; i < N; i++) {
            forks[i] = new Fork2(i);
        }

        for (int i = 0; i < N; i++) {
            phils[i] = new Philosopher2(i, forks[i], forks[(i + N - 1) % N]);
            phils[i].start();
        }
    }
}


class Philosopher2 extends Thread {
    private final int index;
    private final Fork2 left;
    private final Fork2 right;

    public Philosopher2(int index, Fork2 left, Fork2 right) {
        this.index = index;
        this.left = left;
        this.right = right;
    }

    public void run() {
        Random randomGenerator = new Random();
        try {
            while (true) {
                // Lock left, then tryLock right
                Thread.sleep(randomGenerator.nextInt(100)); // not sleeping but thinking
                System.out.println("Phil " + index + " finishes thinking.");
                left.lock.lock();
                System.out.println("Phil " + index + " locks left.");
                left.pickup();
                System.out.println("Phil " + index + " picks up left fork.");
                System.out.println("Phil " + index + " tries to lock right.");
                // The random wait will ensure no live deadlock/livelock
                boolean lockRight =
                        right.lock.tryLock(randomGenerator.nextInt(1000), TimeUnit.MILLISECONDS);
                if (lockRight) {
                    System.out.println("Phil " + index + " locks right.");
                    right.pickup();
                    System.out.println("Phil " + index + " picks up right fork.");
                    // Only eat when the philosopher has both forks
                    Thread.sleep(randomGenerator.nextInt(100)); // eating
                    System.out.println("Phil " + index + " finishes eating.");
                }
                left.putdown();
                System.out.println("Phil " + index + " puts down left fork.");
                left.lock.unlock();
                System.out.println("Phil " + index + " unlocks left.");
                if (lockRight) {
                    right.putdown();
                    System.out.println("Phil " + index + " puts down right fork.");
                    right.lock.unlock();
                    System.out.println("Phil " + index + " unlocks right.");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Don't disturb me while I am sleeping, well, thinking.");
        }
    }
}


class Fork2 {
    private final int index;
    private boolean isAvailable = true;
    public ReentrantLock lock = new ReentrantLock();

    public Fork2(int index) {
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
            return "Fork2 " + index + " is available.";
        } else {
            return "Fork2 " + index + " is NOT available.";
        }
    }
}
