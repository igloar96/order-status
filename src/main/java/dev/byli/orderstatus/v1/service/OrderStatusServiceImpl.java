package dev.byli.orderstatus.v1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.component.TradeOrderStatus;
import dev.byli.orderstatus.v1.repository.TradeStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

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

    @Value("${events.order-status.topic}")
    private String orderStatusTopic;

    @Override
    @Transactional
    public void getOrderStatus(String external_id) {
        this.tradeStatusRepository.findAllByStatusIn(Arrays.asList(OrderStatus.NEW, OrderStatus.PARTIALLY_FILLED)).forEach(status -> {
            status.setStatus(this.orderStatus.getOrderStatus(status.getExternal_id()));
            try {
                this.kafkaTemplate.send(orderStatusTopic, this.objectMapper.writeValueAsString(
                    dev.byli.commons.TradeOrderStatus.builder()
                        .external_id(status.getExternal_id())
                        .ticker_pair_id(status.getTicker_pair_id())
                        .orderStatus(status.getStatus())
                        .build()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
