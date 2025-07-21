package com.back.domain.order.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderUpdateReqBody(
        @NotNull
        int orderNum,
        @NotBlank
        String address,
        @NotNull
        List<OrderItemUpdateReqBody> items
) {
}
