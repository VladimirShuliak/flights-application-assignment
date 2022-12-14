package com.example.flights.application.assignment.services;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;

import java.util.ArrayList;

/**
 * Simple In Memory Cache
 *
 * @cacheMap - map with a fixed maximum size which removes the least recently used entry if an entry is added when full
 * @timeToLive - time in milliseconds
 */
public class LocalInMemoryCache<K, T> {
    private final long timeToLive;
    private final LRUMap cacheMap;


    /**
     * @param timeToLive
     * @param timerInterval
     * @param maxItems
     */
    public LocalInMemoryCache(final long timeToLive, final long timerInterval, int maxItems) {
        this.timeToLive = timeToLive * 1000;
        cacheMap = new LRUMap(maxItems);
        if (timeToLive > 0 && timerInterval > 0) {
            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(timerInterval * 1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    cleanup();
                }
            });
            // Marks this thread as either a daemon thread or a user thread.
            // This method must be invoked before the thread is started.
            t.setDaemon(true);
            // The result is that two threads are running concurrently:
            // the current thread (which returns from the call to the start method) and the other thread (which executes its run method)
            t.start();
        }
    }

    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    public void put(K key, T value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new CacheObject(value));
        }
    }

    public T get(K key) {
        synchronized (cacheMap) {
            CacheObject c;
            c = (CacheObject) cacheMap.get(key);

            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return c.value;
            }
        }
    }

    public void cleanup() {
        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey = null;
        synchronized (cacheMap) {
            MapIterator itr = cacheMap.mapIterator();
            deleteKey = new ArrayList<K>((cacheMap.size() / 2) + 1);
            K key = null;
            CacheObject c = null;
            while (itr.hasNext()) {
                key = (K) itr.next();
                c = (CacheObject) cacheMap.get(key);
                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }
        for (K key : deleteKey) {
            synchronized (cacheMap) {
                cacheMap.remove(key);
            }
            Thread.yield();
        }
    }

    protected class CacheObject {
        public long lastAccessed = System.currentTimeMillis();
        public T value;

        protected CacheObject(T value) {
            this.value = value;
        }
    }
}
