public class Visibility extends Thread {
    boolean keepRunning = true;
    // boolean volatile keepRunning = true;

    public static void main(String[] args) throws InterruptedException {
        Visibility t = new Visibility();
        t.start();
        Thread.sleep(1000);
        t.keepRunning = false; // This change will not affect and be propagated to the while loop in
                               // the run() method since the keepRunning variable has been cached
                               // (to fix, use the volatile keyword)
        System.out.println(System.currentTimeMillis() + ": keepRunning is false");
    }

    public void run() {
        while (keepRunning) {
        }
    }
}
