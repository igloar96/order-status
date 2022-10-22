package dev.byli.orderstatus.v1.component;

import dev.byli.commons.OrderStatus;

public interface TradeOrderStatus {


    OrderStatus getOrderStatus(String external_id);

}
