package dev.byli.orderstatus.v1.service;


import dev.byli.commons.Order;
import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.service.adapter.OrderStatusAdapter;
import dev.byli.orderstatus.v1.service.client.OrderStatusClient;
import org.springframework.stereotype.Service;

@Service
public final class OrderStatusServiceImpl implements OrderStatusService {

    @Override
    public AdapterStep with(OrderStatusClient orderStatusClient) {
        return new AdapterStep() {
            @Override
            public OrderStatusStep and(OrderStatusAdapter orderStatusAdapter) {
                return new OrderStatusStep() {
                    @Override
                    public OrderStatus getOrderStatus(Order order) {
                        return orderStatusAdapter.adapt(orderStatusClient.getOrderStatus(order));
                    }
                };
            }
        };
    }
}
