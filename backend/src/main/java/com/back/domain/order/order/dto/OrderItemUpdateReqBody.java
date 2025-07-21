package com.back.domain.order.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record OrderItemUpdateReqBody(
        @NotNull
        int productId,
        @NotBlank
        String deliveryState,
        @NotNull
        LocalDateTime expectedDeliveryTime,
        @NotNull
        int count
) { }
