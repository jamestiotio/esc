import java.util.Random;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

// Please do take note that generally, it is not recommended to rely on method parameters for
// synchronization (such as by locking about/on them and using them as locks). A possibly better
// idea would be to lock on either this object or some form of a global lock (such as a
// ReentrantLock).
public class FirstExampleFixed {
    // private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Vector<Integer> v = new Vector<>(10);
        int num_threads = 2;
        // Initialize the vector.
        for (int i = 0; i < num_threads * 100; i++) {
            v.add(i);
        }
        Thread[] test = new Thread[num_threads];
        Random r = new Random();
        for (int i = 0; i < num_threads; i++) {
            test[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    // if (r.nextBoolean()) { // This is indeterminate
                    if (j % 2 == 0) {
                        getLast(v);
                    } else {
                        deleteLast(v);
                    }
                }
            });
        }
        for (int item : v) {
            System.out.println(item);
        }
        for (int i = 0; i < num_threads; i++) {
            test[i].start();
        }
        for (int i = 0; i < num_threads; i++) {
            test[i].join();
        }
        for (int item : v) {
            System.out.println(item);
        }
    }

    public static Object getLast(Vector list) {
        synchronized (list) {
            // lock.lock();
            int lastIndex = list.size() - 1;
            Object toReturn = list.get(lastIndex);
            // lock.unlock();
            return toReturn;
        }
    }

    public static void deleteLast(Vector list) {
        synchronized (list) {
            // lock.lock();
            int lastIndex = list.size() - 1;
            list.remove(lastIndex);
            // lock.unlock();
        }
    }

    public static boolean contains(Vector list, Object obj) {
        // Possibility of deadlock here if using synchronized keywords. Might want to implement
        // using a global ReentrantLock instead.
        synchronized (list) {
            synchronized (obj) {
                // lock.lock();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(obj)) {
                        // lock.unlock();
                        return true;
                    }
                }

                // lock.unlock();
                return false;
            }
        }
    }
}
