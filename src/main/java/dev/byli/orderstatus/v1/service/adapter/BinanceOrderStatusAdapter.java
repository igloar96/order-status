package dev.byli.orderstatus.v1.service.adapter;

import dev.byli.commons.Client;
import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.dto.BinanceOrderStatusResponse;
import org.springframework.stereotype.Component;

@Component
public class BinanceOrderStatusAdapter implements OrderStatusAdapter<BinanceOrderStatusResponse> {
    @Override
    public Client getAdapterType() {
        return Client.BINANCE;
    }

    @Override
    public OrderStatus adapt(BinanceOrderStatusResponse object) {
        return OrderStatus.valueOf(object.getStatus());
    }
}
