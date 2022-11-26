package dev.byli.orderstatus.v1.service.client;

import dev.byli.commons.Client;
import dev.byli.commons.Order;
import dev.byli.commons.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MockOrderStatusClient implements OrderStatusClient {

    @Override
    public OrderStatus getOrderStatus(Order order) {
        return OrderStatus.FILLED;
    }

    @Override
    public Client getClientType() {
        return Client.MOCK;
    }
}
