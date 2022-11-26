package dev.byli.orderstatus.v1.controller;

import dev.byli.commons.Client;
import dev.byli.commons.Order;
import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.service.OrderStatusService;
import dev.byli.orderstatus.v1.service.adapter.OrderStatusAdapter;
import dev.byli.orderstatus.v1.service.client.OrderStatusClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/order-status")
@Validated
@Slf4j
public class OrderStatusController {

    @Autowired
    private List<OrderStatusClient> orderStatusClients;
    @Autowired
    private List<OrderStatusAdapter> orderStatusAdapters;

    @Autowired
    private OrderStatusService orderStatusService;

    @PostMapping("/client/{client}")
        public OrderStatus evaluateOrder(@PathVariable Client client,
                                                   @RequestBody Order order) {
        return orderStatusService.with(
                        orderStatusClients
                                .stream()
                                .filter(c -> c.getClientType().compareTo(client)==0)
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("No client found for " + client.name())))
                .and(
                        orderStatusAdapters
                                .stream()
                                .filter(c -> c.getAdapterType().compareTo(client)==0)
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("No adapter found for client " + client.name())))
                .getOrderStatus(order);
    }
}
