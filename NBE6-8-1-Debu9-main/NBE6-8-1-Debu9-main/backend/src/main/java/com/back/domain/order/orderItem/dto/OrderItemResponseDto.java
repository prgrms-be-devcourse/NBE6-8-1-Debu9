package com.back.domain.order.orderItem.dto;

import com.back.domain.order.orderItem.entity.OrderItem;
import com.back.global.util.NumberGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemResponseDto {
    private int orderItem_id; // OrderItem의 id (필드명 변경)
    private String delivery_state;
    private String product_name;
    private String product_eng_name;
    private int price;
    private String image_url;
    private int count;
    private int orderItemNumber; // OrderItem의 고유 번호

    public OrderItemResponseDto(OrderItem orderItem) {
        this.orderItem_id = orderItem.getId(); // OrderItem의 ID 매핑
        this.delivery_state = orderItem.getDeliveryState();
        this.product_name = orderItem.getProduct().getName();
        this.product_eng_name = orderItem.getProduct().getEngName();
        this.price = orderItem.getProduct().getPrice();
        this.image_url = orderItem.getProduct().getImageUrl();
        this.count = orderItem.getCount();
        this.orderItemNumber = NumberGenerator.generateRandomNumber(8);
    }
}