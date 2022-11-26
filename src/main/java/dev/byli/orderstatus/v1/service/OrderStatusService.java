package dev.byli.orderstatus.v1.service;

import dev.byli.commons.Order;
import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.service.adapter.OrderStatusAdapter;
import dev.byli.orderstatus.v1.service.client.OrderStatusClient;

public interface OrderStatusService {
    AdapterStep with(OrderStatusClient orderStatusClient);

    interface AdapterStep {
        OrderStatusStep and(OrderStatusAdapter orderStatusAdapter);
    }

    interface OrderStatusStep {
        OrderStatus getOrderStatus(Order order);
    }

}
