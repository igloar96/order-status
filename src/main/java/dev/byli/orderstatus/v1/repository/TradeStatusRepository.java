package dev.byli.orderstatus.v1.repository;

import dev.byli.commons.OrderStatus;
import dev.byli.orderstatus.v1.model.PendingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TradeStatusRepository extends JpaRepository<PendingOrder, UUID> {
    List<PendingOrder> findAllByStatusIn(List<OrderStatus> statusList);
    Optional<PendingOrder> findByExternalId(String orderId);
}
