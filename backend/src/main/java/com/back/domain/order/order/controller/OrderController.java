package com.back.domain.order.order.controller;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.service.MemberService;
import com.back.domain.order.order.dto.OrderDto;
import com.back.domain.order.order.dto.OrderResponseDto;
import com.back.domain.order.order.dto.OrderUpdateReqBody;
import com.back.domain.order.order.entity.Order;
import com.back.domain.order.order.service.OrderService;
import com.back.domain.order.order.dto.DeliveryDto;
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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "OrderController", description = "API 주문 컨트롤러")
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final OrderItemService orderItemService;

    @GetMapping
    @Transactional(readOnly = true)
    @Operation(summary = "주문 전체 조회")
    public List<OrderResponseDto> getItems() {
        List<Order> items = orderService.findAllWithItemsAndProducts();

        return items
                .stream()
                .map(OrderResponseDto::new)
                .toList();
    }

    @GetMapping("/{orderitem_id}/delivery-schedule")
    @Transactional(readOnly = true)
    @Operation(summary = "배송일정 조회")
    public DeliveryDto getDeliverySchedule(@PathVariable int orderitem_id) {
        System.out.println("ok");
        OrderItem orderItem=orderService.getOrderItem(orderitem_id);

        return new DeliveryDto(orderItem);
    }

    @GetMapping("/{orderNum}")
    @Transactional(readOnly = true)
    @Operation(summary = "주문번호로 단건 조회")
    public OrderResponseDto getItem(@PathVariable int orderNum) {
        Order order = orderService.findByOrderNumWithDetails(orderNum)
                .orElseThrow(() -> new RuntimeException("주문번호 %s에 해당하는 주문을 찾을 수 없습니다.".formatted(orderNum)));

        return new OrderResponseDto(order);
    }

    // email로 조회
    @GetMapping("/search")
    @Transactional(readOnly = true)
    @Operation(summary = "이메일로 주문 조회")
    public List<OrderResponseDto> getItemsByEmail(@RequestParam String memberEmail) { // @RequestParam 사용
        List<Order> orders = orderService.findAllByMemberEmail(memberEmail);

        return orders.stream()
                .map(OrderResponseDto::new)
                .toList();
    }

    record OrderItemWriteReqBody(
            @NotNull
            int productId,
            @NotNull
            int count
    ) { }

    record OrderWriteReqBody(
            @NotNull
            String email,
            @NotBlank
            String address,
            @NotNull
            List<OrderItemWriteReqBody> items
    ) {
    }



    @PostMapping
    @Transactional
    @Operation(summary = "주문 생성")
    public RsData<OrderDto> write(@Valid @RequestBody OrderWriteReqBody reqBody) {
        Optional<Member> nullable_actor = memberService.findByEmail(reqBody.email);
        Member actor = nullable_actor.orElseGet(() -> memberService.save(reqBody.email));
        int orderNum = orderService.generateUniqueNum();
        Order order = orderService.write(actor, orderNum, reqBody.address);

        reqBody.items.stream()
                .map(item ->
                        orderItemService.createOrderItem(
                                order,
                                item.productId,
                                item.count,
                                "배송준비중"
                        )
                ).toList();
        return new RsData<>(
                "201-1",
                "%d번 주문이 생성되었습니다.".formatted(order.getOrderNum()),
                new OrderDto(order)
        );
    }

    @PutMapping
    @Operation(summary = "주문 수정")
    public RsData<OrderDto> update(@Valid @RequestBody OrderUpdateReqBody reqBody) {
        orderService.updateOrder(reqBody);

        return new RsData<>(
                "201-1",
                "%d번 주문이 수정되었습니다.".formatted(reqBody.orderNum())
        );
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "주문 삭제")
    public String deleteOrder(@PathVariable int id) {
        boolean deleted = orderService.delete(id);
        if (deleted) {
            return "주문이 삭제되었습니다.";
        } else {
            return "주문을 찾을 수 없습니다.";
        }
    }


}
