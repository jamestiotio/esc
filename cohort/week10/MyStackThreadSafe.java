class WorkerFixed extends Thread {
    MyStackThreadSafe stack;

    public WorkerFixed(MyStackThreadSafe stack) {
        this.stack = stack;
    }

    public void run() {
        for (int i = 0; i < 1000; i++)
            stack.pop();
    }
}


public class MyStackThreadSafe extends MyStack {
    // pre-condition: s > 0
    // post-condition: maxSize == s && top == -1 && stackArray != null
    public MyStackThreadSafe(int s) {
        super(s); // Do we need "synchronized" for the constructor?
    }

    // pre-condition: top >= 0
    // post-condition: the top element is removed
    public synchronized long pop() {
        long toReturn;

        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        toReturn = stackArray[top--];
        notifyAll();

        assert(top >= -1);
        return toReturn;
    }

    public static void main(String[] args) throws InterruptedException {
        WorkerFixed[] workers = new WorkerFixed[10];
        MyStackThreadSafe stack = new MyStackThreadSafe(10000);
        for (int i = 0; i < 10000; i++)
            stack.push(i);

        System.out.println(stack.top);
        int activeCount = Thread.activeCount();

        for (int i = 0; i < 10; i++) {
            workers[i] = new WorkerFixed(stack);
        }
        for (WorkerFixed worker : workers) {
            worker.start();
        }

        while (Thread.activeCount() > activeCount) {
            Thread.yield();
        }

        System.out.println(stack.top);
    }
}
