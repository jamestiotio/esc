import java.util.Random;

public class BufferExampleFixed {
    public static void main(String[] args) throws Exception {
        Buffer buffer = new Buffer(10);
        int numberProdAndCons = 100000;
        MyProducer[] prodArray = new MyProducer[numberProdAndCons];
        MyConsumer[] consArray = new MyConsumer[numberProdAndCons];

        for (int i = 0; i < numberProdAndCons; i++) {
            prodArray[i] = new MyProducer(buffer);
            prodArray[i].start();

            consArray[i] = new MyConsumer(buffer);
            consArray[i].start();
        }

        for (int i = 0; i < numberProdAndCons; i++) {
            prodArray[i].join();
            consArray[i].join();
        }

        System.out.println(buffer.SIZE);
    }
}


class MyProducer extends Thread {
    private Buffer buffer;

    public MyProducer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        buffer.addItem(new Object());
    }
}


class MyConsumer extends Thread {
    private Buffer buffer;

    public MyConsumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        buffer.removeItem();
    }
}


class Buffer {
    public int SIZE;
    private Object[] objects;
    private int count = 0;

    public Buffer(int size) {
        SIZE = size;
        objects = new Object[SIZE];
    }

    // A synchronized keyword on the method also works (but it's always good to try reduce the
    // scope/size of the critical section and doing lock splitting/stripping)
    public void addItem(Object object) {
        // Prevent multiple threads to concurrently invoke this method and avoids busy-waiting for
        // other threads
        synchronized (this) {
            if (count < SIZE) {
                objects[count] = object;
                count++;
                notifyAll();
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // A synchronized keyword on the method also works (but it's always good to try reduce the
    // scope/size of the critical section and doing lock splitting/stripping)
    public Object removeItem() {
        // Prevent multiple threads to concurrently invoke this method and avoids busy-waiting for
        // other threads
        synchronized (this) {
            if (count > 0) {
                count--;
                notifyAll();
                return objects[count];
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
    }
}
