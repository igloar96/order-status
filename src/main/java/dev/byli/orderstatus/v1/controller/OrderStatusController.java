package dev.byli.orderstatus.v1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.byli.commons.Order;
import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.exception.NotFoundException;
import dev.byli.orderstatus.v1.model.TradeStatus;
import dev.byli.orderstatus.v1.repository.TradeStatusRepository;
import dev.byli.orderstatus.v1.service.OrderStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/order-status")
@Validated
@Slf4j
public class OrderStatusController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TradeStatusRepository tradeStatusRepository;

    @Autowired
    private OrderStatusService orderStatusService;

    @GetMapping("/{externalId}")
    public OrderStatus getOrderStatusByOrderId(@PathVariable("externalId") String externalId) throws NotFoundException {
        return this.orderStatusService.getOrderStatusByExternalId(externalId);
    }


    @GetMapping("/trade/{tickerPairId}")
    public List<TradeStatus> getTradesByTickerPairId(@PathVariable("tickerPairId") UUID tickerPairId) {
        return this.orderStatusService.findAllTradesByTickerPairId(tickerPairId);
    }

    @KafkaListener(topics = "${events.order-routing.orders.topic}", groupId = "order-status")
    public void onNewOrders(String orderResponse) throws  JsonProcessingException {
            Order order = this.objectMapper.readValue(orderResponse, Order.class);
            this.tradeStatusRepository.save(TradeStatus.builder()
                .externalId(order.getExternalId())
                .tickerPairId(order.getTickerPair().getId())
                .status(order.getStatus())
                .build());
    }
}
