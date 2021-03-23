class InterruptExample2 extends Thread {
    private int id;

    public InterruptExample2(int id) {
        this.id = id;
    }

    public void run() {
        for (int i = 1; i <= 10000000; i++) {
            if (this.isInterrupted()) {
                // break;  // handles the interrupt signal.
                try {
                    throw new InterruptedException("being interrupted");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            System.out.println("thread " + id + " printing " + i);
        } // end of for loop
    }

    public static void main(String args[]) throws InterruptedException {
        InterruptExample2 t2 = new InterruptExample2(2);
        t2.start();
        Thread.sleep(100);
        t2.interrupt();
        System.out.println("thread is interrupted? " + t2.isInterrupted());
        System.out.println("thread 2 is alive? " + t2.isAlive());
    }
}
