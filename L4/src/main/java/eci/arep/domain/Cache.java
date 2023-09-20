package eci.arep.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache implements ICache{
    private final Map<String, CacheEntry> lista;
    private final long limitTime;
    private static Cache cache = null;
    public static Cache getInstance(long time) {
        if (cache == null) cache = new Cache(time);
        return cache;
    }

    public int count(){
        return lista.keySet().size();
    }

    private Cache(long time){
        this.lista = new ConcurrentHashMap<>();
        this.limitTime = time;
    }

    public synchronized String get(String key) {
        CacheEntry entry = lista.get(key);
        String val= null;
        if (entry != null) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - entry.timestamp <= limitTime) {
                val = entry.value;
            } else {
                lista.remove(key);
            }
        }

        return val;
    }

    public synchronized void put(String key, String jValue){
        CacheEntry entry = new CacheEntry(jValue, System.currentTimeMillis());
        lista.put(key, entry);
    }

    private static class CacheEntry {
        String value;
        long timestamp;

        CacheEntry(String value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }
}