package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size;
    private double loadFactor;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[16];
        size = 0;
        loadFactor = 0.75;
    }

    public MyHashMap(int initialCapacity) {
        buckets = new Collection[initialCapacity];
        size = 0;
        loadFactor = 0.75;
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        buckets = new Collection[initialCapacity];
        size = 0;
        this.loadFactor = loadFactor;
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new LinkedList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        int hash = key.hashCode();
        int index = Math.floorMod(hash, buckets.length);
        Collection<Node> bucket = buckets[index];
        Node newNode = new Node(key, value);

        if (!containsKey(key)) {
            if (bucket == null) {
                bucket = createBucket();
            }
            bucket.add(newNode);
            buckets[index] = bucket;
            size++;
        }
        else {
            for (Node node : bucket) {
                if (node.key.equals(key)) {
                    node.value = value;
                }
            }
        }

        double check = size / (double) buckets.length;
        if (check >= loadFactor) {
            resizeBuckets();
        }
    }

    private void resizeBuckets() {
        int newCapacity = buckets.length * 2;
        Collection<Node>[] newBuckets = new Collection[newCapacity];
        for (Collection<Node> bucket : buckets) {
            if (bucket != null) {
                for (Node node : bucket) {
                    if (node != null) {
                        int hash = node.key.hashCode();
                        int index = Math.floorMod(hash, newBuckets.length);
                        Collection<Node> newBucket = newBuckets[index];
                        if (newBucket == null) {
                            newBucket = createBucket();
                        }
                        newBucket.add(node);
                        newBuckets[index] = newBucket;
                    }
                }
            }
        }
        buckets = newBuckets;

    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        }
        int hash = key.hashCode();
        int index = Math.floorMod(hash, buckets.length);
        Collection<Node> bucket = buckets[index];
        if (bucket == null) {
            return null;
        }
        else {
            for (Node node : bucket) {
                if (node.key.equals(key)) {
                    return node.value;
                }
            }
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        int hash = key.hashCode();
        int index = Math.floorMod(hash, buckets.length);
        Collection<Node> bucket = buckets[index];
        if (bucket == null) {
            return false;
        }
        else {
            for (Node node : bucket) {
                if (node.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        Arrays.fill(buckets, null);
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            if (bucket != null) {
                for (Node node : bucket) {
                    if (node.key != null) {
                        keySet.add(node.key);
                    }
                }
            }
        }
        return keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        V value;
        if (containsKey(key)) {
            value = get(key);
            int hash = key.hashCode();
            int index = Math.floorMod(hash, buckets.length);
            Collection<Node> bucket = buckets[index];
            if (bucket == null) {
                return null;
            }
            else {
                Collection<Node> newBucket = createBucket();
                for (Node node : bucket) {
                    if (!node.key.equals(key)) {
                        newBucket.add(node);
                    }
                }
                buckets[index] = newBucket;
            }
        }
        else {
            return null;
        }
        return value;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<K> {

        private
        MyIterator() {

        }
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return false;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            return null;
        }
    }
}
