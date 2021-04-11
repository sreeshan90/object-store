package com.theom.challenge.service;

import com.theom.challenge.model.User;
import org.springframework.stereotype.Service;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Service that providers User APIs
 */
@Service
public class UserService {

    private static int BOUND = 100000;
    private static int CACHE_CAPACITY = 100000;
    private static DBService dbService = DBService.getDBInstance();
    private static CacheService cacheService = CacheService.getCacheInstance(CACHE_CAPACITY);
    private static BlockingQueue<User> queue = new LinkedBlockingQueue<>(BOUND);

    static {
        new Thread(new QueueService(queue, dbService)).start();
    }

    /**
     * Method to insert user into the store. This method enqueues the incoming
     * user into a queue and returns immediately. The Users are then inserted into the store
     * in an async fashion
     *
     * @param user
     * @return
     * @throws InterruptedException
     */
    public boolean insertUser(User user) throws InterruptedException {
        queue.put(user);
        return true;
    }

    /**
     *
     * Method to return the user to the caller if present in the store.
     * Logic: first get the user from the cache, if present in the cache,
     * return and add it back cache to maintain recently used status. If not found in cache,
     * get it from the DB, insert into cache and return to caller.
     *
     * @param uuid
     * @return
     */
    public User getUser(String uuid) {

        User user = cacheService.getFromCache(uuid);

        if (user == null) { //cache miss
            user = (User) this.dbService.read(uuid);
        }
        // add to cache
        if (user != null) {
            cacheService.addToCache(user);
        }

        return user;
    }

    /**
     * Method to remove a user from the store. Removes from the
     * cache as well before removing from the store
     * @param uuid
     * @return
     */
    public boolean deleteUser(String uuid) {

        // clear cache before DB - doesn't matter if cache miss
        cacheService.removeFromCache(uuid);

        if (this.dbService.remove(uuid) == null) {
            return false;
        }
        return true;
    }
}
