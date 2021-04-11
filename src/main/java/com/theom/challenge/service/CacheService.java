package com.theom.challenge.service;


import com.theom.challenge.common.LRUCache;
import com.theom.challenge.model.User;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Map;

/**
 * Service that handles Cache related APIs
 */
@Service
public class CacheService {

    private static Map cache;
    private static CacheService cacheInstance;

    public static CacheService getCacheInstance(int capacity) {

        cache = Collections.synchronizedMap(new LRUCache(capacity));
        if (cacheInstance == null) {
            cacheInstance = new CacheService();
        }
        return cacheInstance;
    }

    /**
     *
     * gets the object from cache if present, returns null if not present in cache
     * Time Complexity - O(1)
     * @param id
     * @return
     */
    public User getFromCache(String id) {
        return (User) this.cache.get(id);
    }

    /**
     * inserts object into Cache
     * Time Complexity - O(1)
     * @param user
     */
    public void addToCache(User user) {
        this.cache.put(user.getUuid(), user);
    }

    /**
     * removes object from cache if present or null if there was user.
     * Time Complexity - O(1)
     * @param id
     */
    public void removeFromCache(String id) {
        this.cache.remove(id);
    }

    /**
     * Clears the contents from cache
     */
    public void clear() {
        this.cache.clear();
    }
}
