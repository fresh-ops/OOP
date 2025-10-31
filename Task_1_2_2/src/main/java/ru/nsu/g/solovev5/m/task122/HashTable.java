package ru.nsu.g.solovev5.m.task122;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A hash table class. Uses hash-bucket approach to handle hash collisions.
 *
 * @param <K> a key type
 * @param <V> a value type
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 8;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    private static final int HASH_MIXER_A = 0x85EBCA6B;
    private static final int HASH_MIXER_B = 0xC2B2AE35;

    private int threshold;
    private int capacity;
    private int size;
    private int modCount;
    private Entry<K, V>[] buckets;

    /**
     * Creates a new HashTable with default capacity.
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        capacity = DEFAULT_CAPACITY;
        threshold = (int) (capacity * LOAD_FACTOR_THRESHOLD);
        size = 0;
        modCount = 0;
        buckets = new Entry[capacity];
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HashTable<?, ?> hashTable) || size != hashTable.size) {
            return false;
        }

        for (var entry : hashTable) {
            if (!has(entry.key) || !Objects.equals(get(entry.key), entry.value)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        var hash = 0;

        for (var entry : this) {
            hash += entry.hashCode();
        }

        return hash;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableIterator();
    }

    /**
     * Maps a value to given key. If this key already have a mapping,
     * it replaces old value with a new one and returns it.
     *
     * @param key   a key to map
     * @param value a value to hold
     * @return a prior value, or null if no such was
     */
    public V put(K key, V value) {
        var index = indexOf(key);
        var bucket = buckets[index];

        while (bucket != null) {
            if (Objects.equals(key, bucket.key)) {
                return bucket.setValue(value);
            }

            bucket = bucket.next;
        }

        modCount++;
        if (++size >= threshold) {
            increaseCapacity();
            index = indexOf(key);
        }

        addEntry(key, value, index);
        return null;
    }

    /**
     * Provides a value mapped to a given key.
     *
     * @param key a mapped key
     * @return a mapped value, or null if no such exists
     */
    public V get(Object key) {
        var bucket = buckets[indexOf(key)];

        while (bucket != null) {
            if (Objects.equals(key, bucket.key)) {
                return bucket.value;
            }

            bucket = bucket.next;
        }

        return null;
    }

    /**
     * Returns {@code true} if this table contains given key.
     *
     * @param key a key to check
     * @return {@code true} if this table contains given key, {@code false} otherwise
     */
    public boolean has(Object key) {
        var bucket = buckets[indexOf(key)];

        while (bucket != null) {
            if (Objects.equals(key, bucket.key)) {
                return true;
            }

            bucket = bucket.next;
        }

        return false;
    }

    /**
     * Removes a value mapped to a given key.
     *
     * @param key a mapped key
     * @return a removed value, or null if nothing found
     */
    public V remove(Object key) {
        modCount++;
        var index = indexOf(key);
        var bucket = buckets[index];

        if (bucket == null) {
            return null;
        } else if (Objects.equals(key, bucket.key)) {
            buckets[index] = bucket.next;
            size--;

            bucket.next = null;
            return bucket.value;
        }

        while (bucket.next != null) {
            if (Objects.equals(key, bucket.next.key)) {
                var removing = bucket.next;
                bucket.next = removing.next;
                size--;

                removing.next = null;
                return removing.value;
            }

            bucket = bucket.next;
        }

        return null;
    }

    /**
     * Adds a new entry into the table.
     *
     * @param key   an entry key
     * @param value an entry value
     * @param index an index to put in
     */
    private void addEntry(K key, V value, int index) {
        var entry = new Entry<>(key, value);
        entry.next = buckets[index];
        buckets[index] = entry;
    }

    /**
     * Increases this table capacity and rehashes entries.
     */
    @SuppressWarnings("unchecked")
    private void increaseCapacity() {
        capacity *= 2;
        threshold = (int) (capacity * LOAD_FACTOR_THRESHOLD);
        var oldBuckets = buckets;
        buckets = new Entry[capacity];

        for (Entry<K, V> bucket : oldBuckets) {
            while (bucket != null) {
                var index = indexOf(bucket.key);
                var next = bucket.next;
                bucket.next = buckets[index];
                buckets[index] = bucket;

                bucket = next;
            }
        }
    }

    /**
     * Calculates a hash for a given key.
     *
     * @param key a key to hash
     * @return hashed key that can be used as an index
     */
    private int indexOf(Object key) {
        if (key == null) {
            return 0;
        }

        var hash = key.hashCode();
        hash ^= hash >>> 16;
        hash *= HASH_MIXER_A;
        hash ^= hash >>> 13;
        hash *= HASH_MIXER_B;
        hash ^= hash >>> 16;

        return hash & (capacity - 1);
    }

    /**
     * A class to represent an entry of a table.
     *
     * @param <K> a key type
     * @param <V> a value type
     */
    public static class Entry<K, V> {
        private Entry<K, V> next;
        private final K key;
        private V value;

        /**
         * Creates a new table entry.
         *
         * @param key   an entry key
         * @param value an entry value
         */
        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Entry<?, ?> entry)) {
                return false;
            }

            return Objects.equals(key, entry.key) && Objects.equals(value, entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        /**
         * Returns an entry key.
         *
         * @return an entry key
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns an entry value.
         *
         * @return an entry value
         */
        public V getValue() {
            return value;
        }

        /**
         * Sets the entry value.
         *
         * @param newValue a new entry value
         * @return an old entry value
         */
        public V setValue(V newValue) {
            var oldValue = value;
            value = newValue;

            return oldValue;
        }

        @Override
        public String toString() {
            return String.format("(%s, %s)", key, value);
        }
    }

    private class HashTableIterator implements Iterator<Entry<K, V>> {
        private int remaining;
        private final int knownMod;
        private int lastIndex;
        private Entry<K, V> lastBucket;

        HashTableIterator() {
            this.remaining = size;
            this.lastIndex = -1;
            this.knownMod = modCount;
        }

        @Override
        public boolean hasNext() {
            return remaining > 0;
        }

        @Override
        public Entry<K, V> next() {
            if (knownMod != modCount) {
                throw new ConcurrentModificationException();
            }
            if (remaining <= 0) {
                throw new NoSuchElementException();
            }
            remaining--;

            if (lastBucket != null) {
                lastBucket = lastBucket.next;
            }
            while (lastBucket == null) {
                lastBucket = buckets[++lastIndex];
            }

            return lastBucket;
        }
    }
}
