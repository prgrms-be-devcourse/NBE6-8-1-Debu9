package com.back.domain.order.orderItem.controller;

import com.back.domain.member.member.service.MemberService;
import com.back.domain.order.order.entity.Order;
import com.back.domain.order.order.service.OrderService;
import com.back.domain.order.orderItem.dto.OrderItemDto;
import com.back.domain.order.orderItem.entity.OrderItem;
import com.back.domain.order.orderItem.service.OrderItemService;
import com.back.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders/{orderId}/items") //
@RequiredArgsConstructor
@Tag( name = "OrderItemController", description = "API 주문 아이템 컨트롤러")
public class OrderItemController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final MemberService memberService;

    @GetMapping
    @Transactional(readOnly = true)
    @Operation(summary = "다건 조회")
    public List<OrderItemDto> getItems(
            @PathVariable int orderId
    ) {
        Order order = orderService.findById(orderId).get();

        return order
                .getItems()
                .stream()
                .map(OrderItemDto::new)
                .toList();
    }

    record OrderItemsModifyReqBody(
            @NotNull
            int count,
            @NotNull
            LocalDateTime expectedDeliveryDate,
            @NotBlank
            String deliveryState
    ) {
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "수정")
    public RsData<Void> modify(
            @PathVariable int orderId,
            @PathVariable int id,
            @Valid @RequestBody OrderItemsModifyReqBody reqBody
    ) {
        Order order = orderService.findById(orderId).get();

        OrderItem orderItem = order.findItemById(id).get();

        orderService.modifyitem(orderItem, reqBody.count, reqBody.expectedDeliveryDate, reqBody.deliveryState);

        return new RsData<>(
                "200-1",
                "%d번 주문 상품이 수정되었습니다.".formatted(id)
        );
    }

}
