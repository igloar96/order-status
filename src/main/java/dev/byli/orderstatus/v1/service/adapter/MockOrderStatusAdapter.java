package dev.byli.orderstatus.v1.service.adapter;

import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.dto.Client;
import org.springframework.stereotype.Component;

@Component
public class MockOrderStatusAdapter implements OrderStatusAdapter<OrderStatus>{
    @Override
    public Client getAdapterType() {
        return Client.MOCK;
    }

    @Override
    public OrderStatus adapt(OrderStatus object) {
        return object;
    }
}
