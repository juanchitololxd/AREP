package edu.eci.arep.domain.cache;

import junit.framework.TestCase;

public class CacheTest extends TestCase {

    private Cache cache;

    public void testGet() throws InterruptedException {
        cache = Cache.getInstance(2000);
        String titleTest = "TESTING";
        cache.put(titleTest, "JCONTENT");
        assertNotNull(cache.get(titleTest));
        assertNotNull(cache.get(titleTest)); //dont erase after its found
        Thread.sleep(2001);
        assertNull(cache.get(titleTest));
        cache.put(titleTest, "JCONTENT");
        assertNotNull(cache.get(titleTest));
    }

    public void testPut() {
        cache = Cache.getInstance(5000);
        String titleTest = "TESTING", sContent = "JCONTENT";
        cache.put(titleTest, sContent);
        assertEquals(cache.get(titleTest), sContent);
        cache.put(titleTest, sContent);
        assertEquals(cache.get(titleTest), sContent);
        assertEquals(cache.count(), 1);
        cache.put(titleTest, null);
        assertNull(cache.get(titleTest));

    }
}