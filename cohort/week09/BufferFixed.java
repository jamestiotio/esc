public class BufferFixed {
    public int SIZE;
    private Object[] objects;
    private int count = 0;

    public BufferFixed(int size) {
        SIZE = size;
        objects = new Object[SIZE];
    }

    // Prevent multiple threads to concurrently invoke this method and avoids busy-waiting for other
    // threads
    public synchronized void addItem(Object object) throws Exception {
        while (count == SIZE - 1) {
            wait();
        }

        objects[count] = object;
        count++;
        notifyAll();
    }

    // Prevent multiple threads to concurrently invoke this method and avoids busy-waiting for other
    // threads
    public synchronized Object removeItem() throws Exception {
        while (count == 0) {
            wait();
        }

        count--;
        Object object = objects[count];
        notifyAll();
        return object;
    }
}
