package com.back.domain.order.order.repository;


import com.back.domain.order.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o JOIN FETCH o.items oi JOIN FETCH oi.product p WHERE o.user.email = :email")
    List<Order> findAllByMemberEmailWithItemsAndProducts(String email);

    @Query("SELECT o FROM Order o JOIN FETCH o.items oi JOIN FETCH oi.product p")
    List<Order> findAllWithItemsAndProducts();

    // 주문번호로 단건 조회 시 Order, OrderItem, Product, Member 정보까지 한 번에 가져오는 Fetch Join 쿼리
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.items oi " +
            "JOIN FETCH oi.product p " +
            "JOIN FETCH o.user m " +
            "WHERE o.orderNum = :orderNum")
    Optional<Order> findByOrderNumWithDetails(@Param("orderNum") int orderNum);

    Optional<Order> findByOrderNum(int orderNum);
}

