public class ExperimentFixed2 {
    private static int MY_INT = 0;
    // More memory is needed to be allocated to create the lock, and in terms of time, each thread
    // needs to wait until the lock is released before it can access it
    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        new ChangeListener().start();
        System.out.println("Waiting two seconds so the JIT will probably optimize ChangeListener");
        Thread.sleep(2000);

        new ChangeMaker().start();
    }

    static class ChangeListener extends Thread {
        public void run() {
            int local_value = MY_INT;
            while (local_value < 5) {
                synchronized (lock) {
                    if (local_value != MY_INT) {
                        System.out.println("Got Change for MY_INT : " + MY_INT);
                        local_value = MY_INT;
                    }
                }
            }
        }
    }

    static class ChangeMaker extends Thread {
        public void run() {
            int local_value = MY_INT;
            while (MY_INT < 5) {
                synchronized (lock) {
                    System.out.println("Incrementing MY_INT to " + (local_value + 1));
                    MY_INT = ++local_value;
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
