package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private class Node {
        public T item;
        public Node next;
        public Node prev;
        public Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node sentinel;
    private Node lastSentinel;
    private int size;
    public LinkedListDeque61B() {
        sentinel = new Node(null, null, null);
        lastSentinel = new Node(null, null, null);

        sentinel.next = lastSentinel;
        sentinel.prev = lastSentinel;
        lastSentinel.next = sentinel;
        lastSentinel.prev = sentinel;

        size = 0;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x, sentinel.next, sentinel);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node newNode = new Node(x, lastSentinel, lastSentinel.prev);
        lastSentinel.prev.next = newNode;
        lastSentinel.prev = newNode;
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
        Node current = sentinel.next;
        while (current.next != sentinel) {
            list.add(current.item);
            current = current.next;
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
        if (this.size == 0) {
            return true;
        }
        return false;

        // return size == 0;
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
        if (size == 0) {
            return null;
        }
        Node nextNode = sentinel.next;
        sentinel.next.prev = sentinel;
        sentinel.next = nextNode.next;
        size--;
        return nextNode.item;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node prevNode = lastSentinel.prev;
        prevNode.prev.next = lastSentinel;
        lastSentinel.prev = prevNode.prev;
        size--;
        return prevNode.item;
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
        Node current = sentinel.next;
        for (int i = 0; i < index ; i++) {
            current = current.next;
        }
        return current.item;
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
        Node current = sentinel.next;
        return getRecursive(current, index);
    }

    private T getRecursive(Node current, int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        else if (index == 0) {
            return current.item;
        }
        else {
            return getRecursive(current.next, index - 1);
        }
    }


    @Override
    public Iterator<T> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<T> {
        private Node current;
        public ListIterator() {
            current = sentinel.next;
        }

        public boolean hasNext() {
            return current != lastSentinel;
        }

        public T next() {
            T returnItem = current.item;
            current = current.next;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof LinkedListDeque61B<?>) {
            LinkedListDeque61B<?> that = (LinkedListDeque61B<?>) other;
            if (this.size != that.size()) {
                return false;
            }
            for (int i = 0; i < this.size; i++) {
                if (this.get(i) != that.get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
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

