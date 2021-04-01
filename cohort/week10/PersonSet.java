import java.util.HashSet;
import java.util.Set;

// This class is thread-safe due to encapsulation. mySet is thread-safe since the exposed public
// methods are synchronized, while the mySet object itself is private and does not "participate" in
// non-synchronized public methods or with other attributes with public scope.
public class PersonSet {
    // @guarded by "this"
    private final Set<Person> mySet = new HashSet<>();
    // Note that HashSet is not thread-safe! (as opposed to ConcurrentHashMap)

    public synchronized void addPerson(Person p) {
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Person p) {
        return mySet.contains(p);
    }

    class Person {
        public int x;
    }
}
