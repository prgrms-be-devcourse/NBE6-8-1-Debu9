package com.back.domain.order.order.dto;

import com.back.domain.order.order.entity.Order;
import com.back.domain.order.orderItem.dto.OrderItemResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDto {
    private int order_id; // Order의 id (필드명 변경)
    private int order_number;
    private int total_price;
    private String order_address;
    private int total_count;
    private String created_date;
    private String email;
    private List<OrderItemResponseDto> order_items;

    public OrderResponseDto(Order order) {
        this.order_id = order.getId(); // Order의 ID 매핑
        this.order_number = order.getOrderNum();
        this.order_address = order.getAddress();
        this.created_date = order.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy. MM. dd"));
        this.email = order.getUser().getEmail(); // 주문한 사용자의 이메일

        this.order_items = order.getItems().stream()
                .map(OrderItemResponseDto::new)
                .collect(Collectors.toList());

        this.total_price = this.order_items.stream()
                .mapToInt(item -> item.getPrice() * item.getCount())
                .sum();

        this.total_count = this.order_items.stream()
                .mapToInt(OrderItemResponseDto::getCount)
                .sum();
    }
}