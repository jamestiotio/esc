import java.util.Vector;

// getLast() and deleteLast() cannot be executed concurrently since Vector is synchronized, but the
// corresponding lastIndex values of each can still be wrong
public class FirstExample {
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
