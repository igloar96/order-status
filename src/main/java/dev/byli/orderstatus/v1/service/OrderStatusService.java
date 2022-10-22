package dev.byli.orderstatus.v1.service;

import org.springframework.scheduling.annotation.Scheduled;

public interface OrderStatusService {
    @Scheduled(fixedDelayString = "1000")
    void getOrderStatus(String external_id);
}
