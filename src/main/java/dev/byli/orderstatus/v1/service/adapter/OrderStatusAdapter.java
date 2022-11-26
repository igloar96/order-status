package dev.byli.orderstatus.v1.service.adapter;

import dev.byli.commons.Client;
import dev.byli.commons.OrderStatus;

public interface OrderStatusAdapter<T extends Object> {
    Client getAdapterType();
    OrderStatus adapt(T object);
}
