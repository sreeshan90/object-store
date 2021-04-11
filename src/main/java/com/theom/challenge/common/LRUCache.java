package com.theom.challenge.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Generic Implementation of a Cache with Least recently used eviction policy
 * @param <K>
 * @param <V>
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = 1L;

    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public V get(Object key) {
        return super.getOrDefault(key, null);
    }

    public V put(K key, V value) {
        super.put(key, value);
        return value;
    }

    /**
     * Overriding to remove eldest entry once the capacity is reached
     * @param eldest
     * @return
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}