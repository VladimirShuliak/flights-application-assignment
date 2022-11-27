package com.example.flights.application.assignment.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalInMemoryCacheTest {

    @Test
    void cleanup() throws InterruptedException {
        int size = 3;
        LocalInMemoryCache cache = new LocalInMemoryCache(100, 100, 10);
        for (int i = 0; i < size; i++) {
            String value = Integer.toString(i);
            cache.put(1, value);
        }
        Thread.sleep(100);
        cache.cleanup();

        assertEquals(1, cache.size());
    }
}
