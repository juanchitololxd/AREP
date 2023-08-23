package edu.eci.arep.domain.cache;

import java.util.HashMap;
import java.util.Map;

public interface ICache {

    String get(String key);

    void put(String key, String jValue);
}
