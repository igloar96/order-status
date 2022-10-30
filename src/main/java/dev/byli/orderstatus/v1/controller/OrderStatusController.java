package dev.byli.orderstatus.v1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.byli.commons.Order;
import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.cache.OrderCache;
import dev.byli.orderstatus.v1.exception.NotFoundException;
import dev.byli.orderstatus.v1.model.PendingOrder;
import dev.byli.orderstatus.v1.repository.TradeStatusRepository;
import dev.byli.orderstatus.v1.service.OrderStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
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

    @Autowired
    private OrderCache orderCache;

    @GetMapping("/{externalId}")
    public OrderStatus getOrderStatusByOrderId(@PathVariable("externalId") String externalId) throws NotFoundException {
        return this.orderStatusService.getOrderStatusByExternalId(externalId);
    }

    @KafkaListener(topics = "${events.orders.topic}", groupId = "order-status")
    public void onNewOrders(String orderResponse) throws  JsonProcessingException {
            Order order = this.objectMapper.readValue(orderResponse, Order.class);
            //TODO: move to status strategy
            if(order.getStatus().equals(OrderStatus.NEW)) {
                orderCache.save(order);

                this.tradeStatusRepository.save(PendingOrder.builder()
                        .status(order.getStatus())
                        .externalId(order.getExternalId())
                        .build());
            }
    }
}
