public class BufferFixed {
    public int SIZE;
    private Object[] objects;
    private int count = 0;

    public BufferFixed(int size) {
        SIZE = size;
        objects = new Object[SIZE];
    }

    public synchronized void addItem(Object object) throws Exception {
        while (count == SIZE - 1) {
            wait();
        }

        objects[count] = object;
        count++;
        notifyAll();
    }

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
