public class VisibilityFixed extends Thread {
    volatile boolean keepRunning = true;

    public static void main(String[] args) throws InterruptedException {
        VisibilityFixed t = new VisibilityFixed();
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
