package dev.byli.orderstatus.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.byli.commons.OrderStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE orders SET deleted = true WHERE id=?")
@Where(clause="deleted=false")
public class TradeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    @Column(name="id", columnDefinition = "VARCHAR(255)", insertable = false, updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false,name = "external_id",unique = true)
    private String externalId;

    @Type(type="uuid-char")
    @Column(name="ticker_pair_id", columnDefinition = "VARCHAR(255)", updatable = false, nullable = false)
    private UUID tickerPairId;

    @Column(nullable = false,name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreationTimestamp
    private Date createdAt;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;
}
