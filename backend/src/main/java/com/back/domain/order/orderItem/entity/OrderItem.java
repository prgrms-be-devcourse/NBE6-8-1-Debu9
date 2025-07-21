package com.back.domain.order.orderItem.entity;

import com.back.domain.order.order.entity.Order;
import com.back.domain.product.product.entity.Product;
import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // DB에 생성될 외래 키(FK) 컬럼명
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "product_id") // DB에 생성될 외래 키(FK) 컬럼명
    private Product product;
    private int count;
    private LocalDateTime expectedDeliveryDate;
    private String deliveryState;


    public OrderItem(Order order, Product product, int count, LocalDateTime expectedDeliveryDate, String deliveryState) {
        this.order = order;
        this.product = product;
        this.count = count;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.deliveryState = deliveryState;
    }

    public void modify(int count, LocalDateTime expectedDeliveryDate, String deliveryState) {
        this.count = count;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.deliveryState = deliveryState;
    }

}
