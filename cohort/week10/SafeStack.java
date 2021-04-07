import java.util.ArrayList;
import java.util.List;

/**
 * A Stack is a mutable object representing a last-in-first-out stack of elements (of an arbitrary
 * type E). Elements can be pushed onto the stack, and then popped off in the reverse order that
 * they were pushed. A Stack can hold an arbitrary number of elements.
 */
public class SafeStack<E> {
    // Stack is a *generic* class, which means it takes a type parameter.
    // The type parameter E must be filled in with an object type
    // when using this class: e.g. Stack<String>, Stack<Double>,
    // or Stack<List<Integer>>. From Stack's point of view, however,
    // the element type is just E.

    // The ArrayList implementation is not synchronized (not thread-safe) as mentioned in the
    // official JDK documentation
    private final Stack<E> stack;
    // elems contains the elements in the stack,
    // in order from oldest pushed (elems[0]) to
    // to the latest item pushed, and the
    // next to be popped (elems[size-1]).
    // If elems.size == 0, then the stack is empty.

    /**
     * Make a Stack, initially empty.
     */
    public SafeStack() {
        this.stack = new Stack<>();
    }

    /**
     * Modifies this stack by pushing an element onto it.
     * 
     * @param e element to push on top; the number of elements in elems is incremented by one
     */
    public synchronized void push(E e) {
        this.stack.push(e);
    }

    /**
     * Modifies this stack by popping off the top element. Requires: stack is not empty, i.e. size()
     * > 0.
     * 
     * @return element on top of stack
     */
    public synchronized E pop() {
        return this.stack.pop();
    }

    /**
     * @return number of elements in the stack
     */
    public synchronized int size() {
        return this.stack.size();
    }

    /**
     * Modifies this stack by pushing an element onto it, only if the stack is not full. Else, no
     * changes are made to the stack.
     * 
     * @param e element to push on top; the number of elements in elems is incremented by one
     */
    public synchronized void pushIfNotFull(E e) {
        if (!this.stack.isFull()) {
            this.stack.push(e);
        }
    }

    /**
     * Modifies this stack by popping off the top element, only if the stack is not empty. Else,
     * return null.
     * 
     * @return element on top of stack or null
     */
    public synchronized E popIfNotEmpty() {
        if (this.stack.isEmpty()) {
            return null;
        }

        return this.stack.pop();
    }
}
