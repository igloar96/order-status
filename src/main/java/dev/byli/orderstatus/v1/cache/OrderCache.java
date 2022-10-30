package dev.byli.orderstatus.v1.cache;

import dev.byli.commons.Order;

public interface OrderCache {
    void save(Order order);
    Order order(String externalId);
}
