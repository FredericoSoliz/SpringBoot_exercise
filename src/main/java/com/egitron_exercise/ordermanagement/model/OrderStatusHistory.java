package com.egitron_exercise.ordermanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderStatusHistoryId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime changedAt;

    public Long getOrderStatusHistoryId() { return orderStatusHistoryId; }
    public void setOrderStatusHistoryId(Long orderStatusHistoryId) { this.orderStatusHistoryId = orderStatusHistoryId; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public LocalDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(LocalDateTime changedAt) { this.changedAt = changedAt; }
}
