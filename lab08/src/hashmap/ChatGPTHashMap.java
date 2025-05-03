package hashmap;

import java.util.*;

/**
 * A hash table-backed Map implementation.
 *
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 */
public class ChatGPTHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
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

    /** Default initial capacity and load factor */
    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD = 0.75;

    /** Constructors */
    public ChatGPTHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD);
    }

    public ChatGPTHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD);
    }

    public ChatGPTHashMap(int initialCapacity, double loadFactor) {
        this.buckets = createTable(initialCapacity);
        this.size = 0;
        this.loadFactor = loadFactor;
    }

    /**
     * Factory to create backing table
     */
    @SuppressWarnings("unchecked")
    private Collection<Node>[] createTable(int capacity) {
        return new Collection[capacity];
    }

    /**
     * Factory method for buckets. Override to change bucket type.
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        int idx = indexFor(key);
        Collection<Node> bucket = buckets[idx];
        if (bucket != null) {
            for (Node n : bucket) {
                if (n.key.equals(key)) {
                    return n.value;
                }
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        int idx = indexFor(key);
        Collection<Node> bucket = buckets[idx];
        if (bucket == null) {
            bucket = createBucket();
            buckets[idx] = bucket;
        }
        for (Node n : bucket) {
            if (n.key.equals(key)) {
                n.value = value;
                return;
            }
        }
        bucket.add(new Node(key, value));
        size++;

        if ((double) size / buckets.length > loadFactor) {
            resize(buckets.length * 2);
        }
    }

    private void resize(int newCapacity) {
        Collection<Node>[] old = buckets;
        buckets = createTable(newCapacity);
        size = 0;
        for (Collection<Node> bucket : old) {
            if (bucket != null) {
                for (Node n : bucket) {
                    put(n.key, n.value);
                }
            }
        }
    }

    @Override
    public V remove(K key) {
        int idx = indexFor(key);
        Collection<Node> bucket = buckets[idx];
        if (bucket == null) {
            return null;
        }
        Iterator<Node> iter = bucket.iterator();
        while (iter.hasNext()) {
            Node n = iter.next();
            if (n.key.equals(key)) {
                V val = n.value;
                iter.remove();
                size--;
                return val;
            }
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Collection<Node> bucket : buckets) {
            if (bucket != null) {
                for (Node n : bucket) {
                    keys.add(n.key);
                }
            }
        }
        return keys;
    }

    @Override
    public void clear() {
        Arrays.fill(buckets, null);
        size = 0;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyIterator();
    }

    /**
     * Iterator over keys in the map
     */
    private class MyIterator implements Iterator<K> {
        private int bucketIndex;
        private Iterator<Node> bucketIter;
        private K lastKey;

        MyIterator() {
            bucketIndex = 0;
            advanceBucket();
        }

        private void advanceBucket() {
            bucketIter = null;
            while (bucketIndex < buckets.length) {
                Collection<Node> bucket = buckets[bucketIndex++];
                if (bucket != null && !bucket.isEmpty()) {
                    bucketIter = bucket.iterator();
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            if (bucketIter != null && bucketIter.hasNext()) {
                return true;
            }
            advanceBucket();
            return bucketIter != null && bucketIter.hasNext();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node n = bucketIter.next();
            lastKey = n.key;
            return n.key;
        }

        @Override
        public void remove() {
            if (lastKey == null) {
                throw new IllegalStateException("next() has not been called or remove() already called");
            }
            ChatGPTHashMap.this.remove(lastKey);
            lastKey = null;
        }
    }

    /**
     * Computes array index for a key
     */
    private int indexFor(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    // Optional: implement remove(K key, V value) if Map61B requires
    @Override
    public boolean remove(K key, V value) {
        V cur = get(key);
        if (cur != null && cur.equals(value)) {
            remove(key);
            return true;
        }
        return false;
    }

    // Optional: putAll, values, entrySet can be implemented if needed.
}

