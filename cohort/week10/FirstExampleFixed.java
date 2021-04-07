import java.util.Random;
import java.util.Vector;

// Please do take note that generally, it is not recommended to rely on method parameters for
// synchronization (such as by locking about/on them and using them as locks)
public class FirstExampleFixed {
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
                    // if (r.nextBoolean()) {  // This is indeterminate
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
            int lastIndex = list.size() - 1;
            return list.get(lastIndex);
        }
    }

    public static void deleteLast(Vector list) {
        synchronized (list) {
            int lastIndex = list.size() - 1;
            list.remove(lastIndex);
        }
    }

    public static boolean contains(Vector list, Object obj) {
        synchronized (list) {
            // Possibility of deadlock here? Might want to implement using a global ReentrantLock
            // instead.
            synchronized (obj) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(obj)) {
                        return true;
                    }
                }

                return false;
            }
        }
    }
}
