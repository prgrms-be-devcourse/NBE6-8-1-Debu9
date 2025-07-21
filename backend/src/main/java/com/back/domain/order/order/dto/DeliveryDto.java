package com.back.domain.order.order.dto;

import com.back.domain.order.orderItem.entity.OrderItem;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DeliveryDto {
    private String email;
    private String address;
    private String deliveryState;
    private LocalDateTime expectedDeliveryDate;

    public DeliveryDto(OrderItem orderItem) {
        this.deliveryState=orderItem.getDeliveryState();
        this.email=orderItem.getOrder().getUser().getEmail();
        this.address=orderItem.getOrder().getAddress();
        this.expectedDeliveryDate=orderItem.getExpectedDeliveryDate();
    }

}
