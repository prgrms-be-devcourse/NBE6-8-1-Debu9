package com.back.domain.order.order.service;

import com.back.domain.member.member.entity.Member;
import com.back.domain.order.order.dto.OrderUpdateReqBody;
import com.back.domain.order.order.entity.Order;
import com.back.domain.order.order.repository.OrderRepository;
import com.back.domain.order.orderItem.entity.OrderItem;
import com.back.domain.order.orderItem.repository.OrderItemRepository;
import com.back.domain.product.product.entity.Product;
import com.back.domain.product.product.service.ProductService;
import com.back.global.util.NumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;

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

    @Transactional
    public void updateOrder(OrderUpdateReqBody reqBody) {
        int orderNum = reqBody.orderNum();
        Order order = orderRepository.findByOrderNum(orderNum).orElse(null);
        if (order == null) {
            return;
        }
        List<OrderItem> orderItemArray = new ArrayList<>();
        order.setAddress(reqBody.address());
        Map<Integer, OrderItem> orderItemMap = new HashMap<>();
        order.getItems().forEach((orderItem -> orderItemMap.put(orderItem.getProduct().getId(), orderItem)));
        reqBody.items().forEach(orderItem ->{
            OrderItem item;
            if (orderItemMap.containsKey(orderItem.productId())){
                item = orderItemMap.get(orderItem.productId());
                item.modify(orderItem.count(), orderItem.expectedDeliveryTime(), orderItem.deliveryState());
            }else{
                Product product = productService.findById(orderItem.productId()).orElse(null);
                if (product == null){
                    return;
                }
                item = new OrderItem(order, product, orderItem.count(), orderItem.expectedDeliveryTime(), orderItem.deliveryState());
            }
            orderItemArray.add(item);
        });
        List<OrderItem> items = order.getItems();
        items.clear();
        items.addAll(orderItemArray);
    }
}
