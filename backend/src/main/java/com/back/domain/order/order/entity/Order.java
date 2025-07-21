package com.back.domain.order.order.entity;

import com.back.domain.member.member.entity.Member;
import com.back.domain.order.orderItem.entity.OrderItem;
import com.back.domain.product.product.entity.Product;
import com.back.global.jpa.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "product_orders")
public class Order extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member user;
    @Column(unique = true, nullable = false)
    private int orderNum;
    @Setter
    private String address;

    @OneToMany(mappedBy = "order", fetch = LAZY, cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order(Member user, int orderNum, String address) {
        this.user = user;
        this.orderNum = orderNum;
        this.address = address;
    }

    public Optional<OrderItem> findItemById(int id) {
        return items
                .stream()
                .filter(item -> item.getId() == id)
                .findFirst();
    }

    public OrderItem addItem(Product product, int count, LocalDateTime expectedDeliveryDate, String deliveryState) {
        OrderItem orderItem = new OrderItem(this, product, count, expectedDeliveryDate, deliveryState);
        items.add(orderItem);
        return orderItem;
    }
}
