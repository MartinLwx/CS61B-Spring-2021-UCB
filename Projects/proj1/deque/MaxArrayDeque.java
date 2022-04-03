package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> myComparator;
    /** Constructor */
    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.myComparator = c;
    }

    /** Return the maximum element */
    public T max() {
        return max(myComparator);
    }

    /** Return the maximum element governed by the comparator */
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T maxVal = get(0);
        for (int i = 1; i < size(); i++) {
            T currentVal = get(i);
            if (c.compare(currentVal, maxVal) > 0) {
                maxVal = currentVal;
            }
        }
        return maxVal;
    }
}
