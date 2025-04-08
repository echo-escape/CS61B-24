package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] array;
    private int size;
    private int fullSize;
    private int firstIndex;
    private int lastIndex;

    public ArrayDeque61B() {
        array = (T[]) new Object[8];
        fullSize = 8;
        firstIndex = 0;
        lastIndex = 1;
        size = 0;
    }

    private void resizeUp() {
        int newCapacity = fullSize * 2;
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = get(i);
        }
        array = newArray;
        fullSize = newCapacity;
        firstIndex = newCapacity - 1;
        lastIndex = size;
    }

    private void resizeDown() {
        int newCapacity = fullSize / 2;
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = get(i);
        }
        array = newArray;
        fullSize = newCapacity;
        firstIndex = newCapacity - 1;
        lastIndex = size;
    }

    @Override
    public void addFirst(T x) {
        if (size == array.length) {
            resizeUp();
        }
        array[firstIndex] = x;
        firstIndex = Math.floorMod(--firstIndex, fullSize);
        size++;
    }


    @Override
    public void addLast(T x) {
        if (size == array.length) {
            resizeUp();
        }
        array[lastIndex] = x;
        lastIndex = Math.floorMod(++lastIndex, fullSize);
        size++;
    }


    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(get(i));
        }
        return list;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public T removeFirst() {
        double usage = 1.0 * size / fullSize;
        if (usage < 0.25) {
            resizeDown();
        }
        int index = Math.floorMod(firstIndex + 1, fullSize);
        T temp = array[index];
        array[index] = null;
        firstIndex = index;
        size--;
        return temp;
    }


    @Override
    public T removeLast() {
        double usage = 1.0 * size / fullSize;
        if (usage < 0.25) {
            resizeDown();
        }
        int index = Math.floorMod(lastIndex - 1, fullSize);
        T temp = array[index];
        array[index] = null;
        lastIndex--;
        size--;
        return temp;
    }


    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int temp = Math.floorMod(firstIndex + index + 1, fullSize);
        return array[temp];
    }


    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<T> {
        private int innerIndex;

        public ArrayIterator() {
            innerIndex = 0;
        }

        public boolean hasNext() {
            return innerIndex < size;
        }

        public T next() {
            T returnItem = get(innerIndex);
            innerIndex++;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ArrayDeque61B)) {
            return false;
        }
        ArrayDeque61B<?> other = (ArrayDeque61B<?>) obj;
        if (size != other.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (this.get(i) != other.get(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < this.size - 1; i++) {
            sb.append(get(i).toString() + ", ");
        }
        sb.append(get(this.size - 1).toString());
        sb.append("]");
        return sb.toString();
    }
}

