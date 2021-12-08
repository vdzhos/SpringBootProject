package com.sbproject.schedule.cacheManager;

import com.sbproject.schedule.utils.Markers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class CustomCacheManager implements CacheManager, InitializingBean {
    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap(16);
    private static final Logger logger = LogManager.getLogger(CustomCacheManager.class);

    public CustomCacheManager() { }

    @Override
    public void afterPropertiesSet() { }

    @Override
    public Cache getCache(String name) {
        Cache cache = this.cacheMap.get(name);
        if (cache == null) {
            synchronized(this.cacheMap) {
                cache = this.cacheMap.get(name);
                if (cache == null) {
                    cache = new ConcurrentMapCache(name, new ConcurrentHashMap(256), false);
                    this.cacheMap.put(name, cache);
                    logger.info(Markers.CUSTOM_CACHING_MARKER, "CREATE NEW CACHE by name " + name);
                }
            }
        }
        else {
            logger.info(Markers.CUSTOM_CACHING_MARKER, "GET EXISTING CACHE by name " + name);
        }
        return cache;
    }

    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }

    public void setCacheNames(@Nullable Collection<String> cacheNames) {
        if (cacheNames != null) {
            Iterator it = cacheNames.iterator();
            while(it.hasNext()) {
                String name = (String)it.next();
                this.cacheMap.put(name, new ConcurrentMapCache(name, new ConcurrentHashMap(256), false));
            }
        }
    }
}

