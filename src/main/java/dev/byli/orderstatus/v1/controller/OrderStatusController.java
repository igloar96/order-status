package dev.byli.orderstatus.v1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.byli.commons.Order;
import dev.byli.orderstatus.v1.model.TradeStatus;
import dev.byli.orderstatus.v1.repository.TradeStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/order-status")
@Validated
@Slf4j
public class OrderStatusController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TradeStatusRepository tradeStatusRepository;

    @KafkaListener(topics = "${events.order-routing.orders.topic}", groupId = "order-status")
    public void onNewOrders(String orderResponse) throws  JsonProcessingException {
            Order order = this.objectMapper.readValue(orderResponse, Order.class);
            this.tradeStatusRepository.save(TradeStatus.builder()
                .external_id(order.getExternal_id())
                .ticker_pair_id(order.getTickerPair().getId())
                .status(order.getStatus())
                .build());
    }
}
