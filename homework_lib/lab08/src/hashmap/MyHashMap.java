package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author HungerBar
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

    private double loadFactor;
    private int initialCapacity;
    private Collection<Node>[] buckets;
    private int size;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this.loadFactor = 0.75;
        this.initialCapacity = 16;
        this.size = 0;

        buckets = (Collection<Node>[]) new Collection[initialCapacity];

        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket(); // 关键：使用工厂方法而非直接new
        }
    }

    public MyHashMap(int initialCapacity) {
        this.loadFactor = 0.75;
        this.initialCapacity = initialCapacity;
        this.size = 0;

        buckets = (Collection<Node>[]) new Collection[initialCapacity];

        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.loadFactor = loadFactor;
        this.initialCapacity = initialCapacity;
        this.size = 0;
        buckets = (Collection<Node>[]) new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();
        }
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
            return new LinkedList<>();
    }

    @Override
    public void put(K key, V value) {
        Node p = findhelper(key);
        if(p!=null) {
            p.value = value;
            return;
        }

        size ++;
        Node newNode = new Node(key, value);
        if(((double)size/initialCapacity) >= loadFactor) {
            int oldCapacity = initialCapacity;
            int newCapacity = oldCapacity * 2;
            Collection<Node>[] newBuckets = (Collection<Node>[]) new Collection[newCapacity];
            for (int i = 0; i < newCapacity; i++) {
                newBuckets[i] = createBucket();
            }
            for (Collection<Node> bucket : buckets) {
                for (Node node : bucket) {
                    int hash = node.key.hashCode();
                    hash = Math.floorMod(hash, newCapacity);
                    newBuckets[hash].add(node);
                }
            }

            this.buckets = newBuckets;
            this.initialCapacity = newCapacity;
        }

        int hash = key.hashCode();
        hash = Math.floorMod(hash, initialCapacity);
        buckets[hash].add(newNode);
    }

    private Node findhelper(K key) {
        int hash = key.hashCode();
        hash = Math.floorMod(hash, initialCapacity);
        for(Node node : buckets[hash]) {
            if(node.key.equals(key)) {
                return node;
            }
        }

        return null;
    }

    @Override
    public V get(K key) {
        Node p = null;
        if(!containsKey(key)) {
            return null;
        }
        int hash = key.hashCode();
        hash = Math.floorMod(hash, initialCapacity);
        for(Node node : buckets[hash]) {
            if(node.key.equals(key)) {
                p = node;
                break;
            }
        }

        return p.value;
    }

    @Override
    public boolean containsKey(K key) {
        int hash = key.hashCode();
        hash = Math.floorMod(hash, initialCapacity);
        for(Node node : buckets[hash]) {
            if(node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for(Collection<Node> bucket : buckets){
            bucket.clear();
        }
        size = 0;

    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("This list is read-only");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("This list is read-only");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("This list is read-only");
    }

}
