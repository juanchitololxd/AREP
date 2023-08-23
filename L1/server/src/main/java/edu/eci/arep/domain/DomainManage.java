package edu.eci.arep.domain;

import edu.eci.arep.domain.cache.Cache;

public class DomainManage {
    public static Cache getCache(long time){
        return Cache.getInstance(time);
    }
}
