import java.util.Random;

public class DiningPhilFixed1 {
    private static int N = 5;

    public static void main(String[] args) throws Exception {
        Philosopher1[] phils = new Philosopher1[N];
        Fork1[] forks = new Fork1[N];

        for (int i = 0; i < N; i++) {
            forks[i] = new Fork1(i);
        }

        for (int i = 0; i < N; i++) {
            phils[i] = new Philosopher1(i, forks[i], forks[(i + N - 1) % N]);
            phils[i].start();
        }
    }
}


class Philosopher1 extends Thread {
    private final int index;
    private final Fork1 left;
    private final Fork1 right;

    public Philosopher1(int index, Fork1 left, Fork1 right) {
        this.index = index;
        this.left = left;
        this.right = right;
    }

    public void run() {
        Random randomGenerator = new Random();
        try {
            while (true) {
                // This even-odd global ordering will ensure that none of the philosophers will try
                // to grab the same forks with the same hands
                if (index % 2 == 0) {
                    // Even-numbered philosophers grab left then right
                    Thread.sleep(randomGenerator.nextInt(100)); // not sleeping but thinking
                    System.out.println("Phil " + index + " finishes thinking.");
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
                } else {
                    // Odd-numbered philosophers grab right then left
                    Thread.sleep(randomGenerator.nextInt(100)); // not sleeping but thinking
                    System.out.println("Phil " + index + " finishes thinking.");
                    right.pickup();
                    System.out.println("Phil " + index + " picks up right fork.");
                    left.pickup();
                    System.out.println("Phil " + index + " picks up left fork.");
                    Thread.sleep(randomGenerator.nextInt(100)); // eating
                    right.putdown();
                    System.out.println("Phil " + index + " puts down right fork.");
                    left.putdown();
                    System.out.println("Phil " + index + " puts down left fork.");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Don't disturb me while I am sleeping, well, thinking.");
        }
    }
}


class Fork1 {
    private final int index;
    private boolean isAvailable = true;

    public Fork1(int index) {
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
            return "Fork1 " + index + " is available.";
        } else {
            return "Fork1 " + index + " is NOT available.";
        }
    }
}
