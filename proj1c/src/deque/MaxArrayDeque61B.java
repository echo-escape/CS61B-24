package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private Comparator<T> compator;
    public MaxArrayDeque61B(Comparator<T> c) {
        compator = c;
    }

    public T max() {
        return max(compator);
    }

    public T max(Comparator<T> c) {
        if (c == null) {
            return null;
        }
        if (size() == 0) {
            return null;
        }
        int maxDex = 0;
        for (int i = 0; i < size(); i++) {
            int cmp = compator.compare(get(maxDex), get(i));
            if (cmp < 0) {
                maxDex = i;
            }
        }
        return get(maxDex);
    }
}
