import java.util.ArrayList;
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
    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        if (size == array.length) {
            resizeUp();
        }
        array[firstIndex] = x;
        firstIndex = Math.floorMod(--firstIndex, fullSize);
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if (size == array.length) {
            resizeUp();
        }
        array[lastIndex] = x;
        lastIndex = Math.floorMod(++lastIndex, fullSize);
        size++;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(get(i));
        }
        return list;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
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

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
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

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int temp = Math.floorMod(firstIndex + index + 1, fullSize);
        return array[temp];
    }


    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
