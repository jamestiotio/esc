public class Visibility extends Thread {
    boolean keepRunning = true;

    public static void main(String[] args) throws InterruptedException {
        Visibility t = new Visibility();
        t.start();
        Thread.sleep(1000);
        t.keepRunning = false;
        System.out.println(System.currentTimeMillis() + ": keepRunning is false");
    }

    public void run() {
        while (keepRunning) {
        }
    }
}
