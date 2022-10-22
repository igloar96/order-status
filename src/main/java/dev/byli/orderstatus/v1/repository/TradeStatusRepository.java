package dev.byli.orderstatus.v1.repository;

import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.model.TradeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TradeStatusRepository extends JpaRepository<TradeStatus, UUID> {
    List<TradeStatus> findAllByStatusIn(List<OrderStatus> statusList);
}
