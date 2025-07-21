package com.back.domain.order.orderItem.service;


import com.back.domain.order.order.entity.Order;
import com.back.domain.order.orderItem.entity.OrderItem;
import com.back.domain.order.orderItem.repository.OrderItemRepository;
import com.back.domain.product.product.entity.Product;
import com.back.domain.product.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;

    @Transactional
    public OrderItem createOrderItem(Order order, int productId, int count, String deliveryState) {
        LocalDateTime expectedDeliveryDate = LocalDateTime.now().plusDays(3).withHour(14).withMinute(0).withSecond(0);
        Optional<Product> nullableProduct = productService.findById(productId);
        if (nullableProduct.isEmpty()) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist.");
        }

        OrderItem orderItem = new OrderItem(order, nullableProduct.get(), count, expectedDeliveryDate, deliveryState);
        return orderItemRepository.save(orderItem);
    }

}
