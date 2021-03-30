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


public class MyStackThreadSafeComplete extends MyStack {
    // pre-condition: s > 0
    // post-condition: maxSize == s && top == -1 && stackArray != null
    public MyStackThreadSafeComplete(int s) {
        super(s); // The "synchronized" keyword is illegal and not allowed for constructors
    }

    // pre-condition: top < stackArray.length
    // post-condition: new element is inserted and added/allocated as the new top
    public synchronized void push(long j) {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        stackArray[++top] = j;
        notifyAll(); // notify all other threads that the state has changed
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
        notifyAll(); // notify all other threads that the state has changed
        return toReturn;
    }

    // pre-condition: top >= 0
    // post-condition: return the current top element
    public synchronized long peek() {
        long toReturn;

        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        toReturn = stackArray[top];
        notifyAll(); // notify all other threads that the state has changed
        return toReturn;
    }

    // pre-condition: true
    // post-condition: the elements are unchanged and the return value is true iff the stack is
    // empty.
    public synchronized boolean isEmpty() {
        return (top == -1);
    }

    // pre-condition: true
    // post-condition: the elements are unchanged and the return value is true iff the stack is
    // full.
    public synchronized boolean isFull() {
        return (top == maxSize - 1);
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
