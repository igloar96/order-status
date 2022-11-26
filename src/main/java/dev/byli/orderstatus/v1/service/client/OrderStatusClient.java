package dev.byli.orderstatus.v1.service.client;

import dev.byli.commons.Client;
import dev.byli.commons.Order;

public interface OrderStatusClient<T> {

    Client getClientType();
    T getOrderStatus(Order order);

}
