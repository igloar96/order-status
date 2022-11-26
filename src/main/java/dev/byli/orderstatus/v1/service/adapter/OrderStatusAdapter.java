package dev.byli.orderstatus.v1.service.adapter;

import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.dto.Client;

public interface OrderStatusAdapter<T extends Object> {
    Client getAdapterType();
    OrderStatus adapt(T object);
}
