package dev.byli.orderstatus.v1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.byli.commons.Order;
import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.cache.OrderCache;
import dev.byli.orderstatus.v1.component.TradeOrderStatus;
import dev.byli.orderstatus.v1.exception.NotFoundException;
import dev.byli.orderstatus.v1.repository.TradeStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    @Autowired
    private TradeStatusRepository tradeStatusRepository;

    @Autowired
    private TradeOrderStatus orderStatus;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${events.orders.topic}")
    private String orderStatusTopic;

    @Autowired
    private OrderCache orderCache;
    @Override
    @Scheduled(fixedDelay = 1,timeUnit = TimeUnit.MINUTES)
    public void getOrderStatus() {
        this.tradeStatusRepository.findAllByStatusIn(Arrays.asList(OrderStatus.NEW, OrderStatus.PARTIALLY_FILLED)).forEach(pendingOrder -> {
            OrderStatus newStatus = this.orderStatus.getOrderStatus(pendingOrder.getExternalId());
            pendingOrder.setStatus(newStatus);
            this.tradeStatusRepository.saveAndFlush(pendingOrder);
            try {
                Order orderEvent = this.orderCache.order(pendingOrder.getExternalId());
                orderEvent.setStatus(newStatus);
                this.kafkaTemplate.send(orderStatusTopic, this.objectMapper.writeValueAsString(orderEvent));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public OrderStatus getOrderStatusByExternalId(String externalId) throws NotFoundException {
        return this.tradeStatusRepository.findByExternalId(externalId).orElseThrow(NotFoundException::new).getStatus();
    }

}
