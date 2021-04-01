import java.util.Vector;

// Please do take note that generally, it is not recommended to use and rely on method parameters
// for synchronization
public class FirstExampleFixed {
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
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(obj)) {
                    return true;
                }
            }

            return false;
        }
    }
}
