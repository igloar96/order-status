package dev.byli.orderstatus.v1.cache;

import dev.byli.commons.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCacheImpl implements OrderCache {

    @Autowired
    private CacheManager cacheManager;

    public void save(Order order){
        this.cacheManager.getCache("os_order_external_id").put(order.getExternalId(),order);
      log.info("Saving order in cache.");
    }
    public Order order(String externalId){
        return this.cacheManager.getCache("os_order_external_id").get(externalId,Order.class);
    }
}
