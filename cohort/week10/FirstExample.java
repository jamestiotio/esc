import java.util.Random;
import java.util.Vector;

// getLast() and deleteLast() cannot be executed concurrently since Vector is synchronized, but the
// corresponding lastIndex values of each can still be wrong
public class FirstExample {
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
                    if (r.nextBoolean()) {
                        getLast(v);
                    } else
                        deleteLast(v);
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
        int lastIndex = list.size() - 1;
        return list.get(lastIndex);
    }

    public static void deleteLast(Vector list) {
        int lastIndex = list.size() - 1;
        list.remove(lastIndex);
    }

    public static boolean contains(Vector list, Object obj) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(obj)) {
                return true;
            }
        }

        return false;
    }
}
