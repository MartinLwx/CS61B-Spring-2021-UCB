package deque;

import java.util.Iterator;

/** Circular implementation */
public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;

    // the next position to insert
    // [...,nextFirst, (real data), nextLast, ...]
    private int nextFirst;
    private int nextLast;

    private static final int INIT_SIZE = 8;
    private static final double USAGE_RATIO = 0.25;
    private static final int RESIZE_FACTOR = 2;

    /** Constructor */
    public ArrayDeque() {
        items = (T[]) new Object[INIT_SIZE];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /** Resize */
    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        int startPos = addOne(nextFirst);
        for (int i = 0; i < size; i++) {
            newItems[i] = items[startPos];
            startPos = addOne(startPos);
        }
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    /** Add one element circularly */
    private int addOne(int index) {
        return (index + 1) % items.length;
    }

    /** Minus one element circularly */
    private int minusOne(int index) {
        return (index + items.length - 1) % items.length;
    }

    @Override
    public void addFirst(T item) {
        if (isFull()) {
            resize(items.length * RESIZE_FACTOR);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (isFull()) {
            resize(items.length * RESIZE_FACTOR);
        }
        items[nextLast] = item;
        nextLast = addOne(nextLast);
        size += 1;
    }

    private boolean isFull() {
        return size == items.length;
    }

    /** isSparse = true if the usage ratior < 0.25 */
    private boolean isSparse() {
        return items.length >= 16 && size * 1.0 / items.length < USAGE_RATIO;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (!isEmpty()) {
            System.out.print(items[addOne(nextFirst)]);
            for (int i = addOne(addOne(nextFirst)); i != nextLast; i = addOne(i)) {
                System.out.print(" " + items[i]);
            }
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (isSparse()) {
            resize(items.length / RESIZE_FACTOR);
        }
        nextFirst = addOne(nextFirst);
        T tmp = items[nextFirst];
        items[nextFirst] = null;        // For GC
        if (!isEmpty()) {
            size -= 1;
        }
        return tmp;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (isSparse()) {
            resize(items.length / RESIZE_FACTOR);
        }
        nextLast = minusOne(nextLast);
        T tmp = items[nextLast];
        items[nextLast] = null;
        if (!isEmpty()) {
            size -= 1;
        }
        return tmp;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int startPos = addOne(nextFirst);
        return items[(startPos + index) % items.length];
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null) {
            return false;
        }
        // ArrayDeque.equals(LinkedListDeque) ?
        if (!(otherObject instanceof Deque)) {
            return false;
        }
        Deque<?> other = (Deque<?>) otherObject;
        if (this.size() != other.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int index;
        ArrayDequeIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            T item = get(index);
            index++;
            return item;
        }
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
}
