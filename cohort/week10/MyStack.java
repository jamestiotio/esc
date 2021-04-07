class Worker extends Thread {
    MyStack stack;

    public Worker(MyStack stack) {
        this.stack = stack;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            stack.pop();
            stack.push(i);
        }
    }
}


public class MyStack {
    protected final int maxSize;
    protected long[] stackArray;    // guarded by "this"
    protected int top;  //invariant: top < stackArray.length && top >= -1; guarded by "this"

    public MyStack(int s) {
        maxSize = s;
        stackArray = new long[maxSize];
        top = -1;
    }

    // pre-condition: top < maxSize
    // post-condition: add new element as the new top
    public void push(long j) {
        stackArray[++top] = j;
    }

    public long pop() {
        return stackArray[top--];
    }

    public long peek() {
        return stackArray[top];
    }

    public boolean isEmpty() {
        return (top == -1);
    }

    public boolean isFull() {
        return (top == maxSize - 1);
    }

    public static void main(String[] args) throws InterruptedException {
        Worker[] workers = new Worker[10];
        MyStack stack = new MyStack(10000);
        for (int i = 0; i < 10000; i++)
            stack.push(i);

        System.out.println(stack.top);
        int activeCount = Thread.activeCount();

        for (int i = 0; i < 10; i++) {
            workers[i] = new Worker(stack);
        }
        for (Worker worker : workers) {
            worker.start();
        }

        while (Thread.activeCount() > activeCount) {
            Thread.yield();
        }

        System.out.println(stack.top);

    }
}
