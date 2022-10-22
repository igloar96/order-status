package dev.byli.orderstatus.v1.component;

import dev.byli.commons.OrderStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BinanceTradeOrderStatus implements TradeOrderStatus {

    @Override
    public OrderStatus getOrderStatus(String external_id) {
        return null;
    }
}
