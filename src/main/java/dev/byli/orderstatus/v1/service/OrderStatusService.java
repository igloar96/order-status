package dev.byli.orderstatus.v1.service;

import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.exception.NotFoundException;
import dev.byli.orderstatus.v1.model.TradeStatus;

import java.util.List;
import java.util.UUID;

public interface OrderStatusService {
    void getOrderStatus();

    OrderStatus getOrderStatusByExternalId(String externalId) throws NotFoundException;

    List<TradeStatus> findAllTradesByTickerPairId(UUID tickerPairId);
}
