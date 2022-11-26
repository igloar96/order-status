package dev.byli.orderstatus.v1.service.client;

import dev.byli.commons.Order;
import dev.byli.orderstatus.v1.dto.Client;

public interface OrderStatusClient<T> {

    Client getClientType();
    T getOrderStatus(Order order);

}
