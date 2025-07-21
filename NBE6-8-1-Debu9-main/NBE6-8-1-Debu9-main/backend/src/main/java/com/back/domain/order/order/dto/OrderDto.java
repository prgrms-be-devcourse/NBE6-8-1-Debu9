package com.back.domain.order.order.dto;

import com.back.domain.order.order.entity.Order;

import java.time.LocalDateTime;

public record OrderDto (
        int id,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        int userId,
        int orderNum,
        String address
)
{
    public OrderDto(Order order) {
        this(
                order.getId(),
                order.getCreateDate(),
                order.getModifyDate(),
                order.getUser().getId(),
                order.getOrderNum(),
                order.getAddress()
        );
    }
}