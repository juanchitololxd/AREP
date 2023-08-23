package edu.eci.arep.domain.cache;

import java.util.HashMap;
import java.util.Map;

public interface ICache {

    /**
     * Returns the value that was saved on cache. Return null if it doesnt exist
     * @param key
     */
    String get(String key);

    /**
     * Add or set the value of the key
     * @param key
     * @param jValue value of key
     */
    void put(String key, String jValue);
}
