package com.theom.challenge.service;

import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.springframework.stereotype.Service;

/**
 * Class that mimics a database service
 */
@Service
public class DBService {

    private static BTreeMap db; // This is a thread-safe data structure where all the user objects are stored
    private static DB dbMaker;
    private static DBService dbService;
    public static DBService getDBInstance() {

        if (dbMaker == null) {
            dbMaker = DBMaker.memoryDB().make();
        }
        if (db == null) {
            db = dbMaker.treeMap("btree", Serializer.STRING, Serializer.JAVA).createOrOpen();
        }

        if (dbService == null) {
            dbService = new DBService();
        }

        return dbService;
    }

    /**
     * Inserts into DB table
     * Time Complexity - O(b log n) because, in the worst case,
     * we need to shuffle around O(b) keys in a node at each level to keep the sorted order property
     * @param key
     * @param value
     * @return
     */
    public Object insert(Object key, Object value) {
        return this.db.put(key, value);
    }


    /**
     * Reads entry from the DB table for given key, returns null if not present
     * Time Complexity - O(log n)
     * @param key
     * @return
     */
    public Object read(Object key) {
        return this.db.get(key);
    }

    /**
     * Removes entry from the table for given key, returns null if not present
     * Time Complexity - O(b log n) — B-Trees must be balanced by definition, and the re-balancing is O(b log n)
     * because, in the worst case, we need to shuffle around O(b) keys in a node at each level to keep the sorted order property
     * @param key
     * @return
     */
    public Object remove(Object key) {
        return this.db.remove(key);
    }
}
