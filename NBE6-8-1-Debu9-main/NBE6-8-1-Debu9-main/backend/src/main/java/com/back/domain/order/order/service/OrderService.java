package com.back.domain.order.order.service;

import com.back.domain.member.member.entity.Member;
import com.back.domain.order.order.entity.Order;
import com.back.domain.order.order.repository.OrderRepository;
import com.back.domain.order.orderItem.entity.OrderItem;
import com.back.domain.order.orderItem.repository.OrderItemRepository;
import com.back.global.util.NumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public long count() {
        return orderRepository.count();
    }

    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    public Order write(Member user, int orderNum, String address) {
        Order order = new Order(user, orderNum, address);

        return orderRepository.save(order);
    }

    public int generateUniqueNum() {
        return NumberGenerator.generateRandomNumber(6);
    }

    public boolean delete(int id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void modifyitem(OrderItem orderItem, int count, LocalDateTime expectedDeliveryDate, String deliveryState) {
        orderItem.modify(count, expectedDeliveryDate, deliveryState);
    }

    public List<Order> findAllByMemberEmail(String email) {
        return orderRepository.findAllByMemberEmailWithItemsAndProducts(email);
    }

    public List<Order> findAllWithItemsAndProducts() {
        return orderRepository.findAllWithItemsAndProducts();
    }

    public Optional<Order> findByOrderNumWithDetails(int orderNum) {
        return orderRepository.findByOrderNumWithDetails(orderNum);
    }

    public void flush() {
        orderRepository.flush();
    }

    @Transactional
    public OrderItem getOrderItem(int OrderItem_Id){
        return orderItemRepository.findById(OrderItem_Id).orElse(null);
    }
}
