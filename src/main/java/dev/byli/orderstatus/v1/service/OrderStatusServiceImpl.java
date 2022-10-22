package dev.byli.orderstatus.v1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.component.TradeOrderStatus;
import dev.byli.orderstatus.v1.exception.NotFoundException;
import dev.byli.orderstatus.v1.model.TradeStatus;
import dev.byli.orderstatus.v1.repository.TradeStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    @Scheduled(fixedDelay = 1000)

    public void getOrderStatus() {
        this.tradeStatusRepository.findAllByStatusIn(Arrays.asList(OrderStatus.NEW, OrderStatus.PARTIALLY_FILLED)).forEach(status -> {
            status.setStatus(this.orderStatus.getOrderStatus(status.getExternalId()));
            try {
                this.kafkaTemplate.send(orderStatusTopic, this.objectMapper.writeValueAsString(
                    dev.byli.commons.TradeOrderStatus.builder()
                        .external_id(status.getExternalId())
                        .ticker_pair_id(status.getTickerPairId())
                        .orderStatus(status.getStatus())
                        .build()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public OrderStatus getOrderStatusByExternalId(String externalId) throws NotFoundException {
        return this.tradeStatusRepository.findByExternalId(externalId).orElseThrow(NotFoundException::new).getStatus();
    }

    @Override
    public List<TradeStatus> findAllTradesByTickerPairId(UUID tickerPairId){
        return this.tradeStatusRepository.findAllByTickerPairId(tickerPairId);
    }
}
