package com.example.Order.Service.repository;

import com.example.Order.Service.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    @Query(value = "Select * from orders",
            countQuery = "Select count(*) from orders",
            nativeQuery = true)
    Page<Order> findAll(Pageable pageable);

    @Query(value = "select o from Order o where o.userId = :userId")
    Page<Order> findByUserId(String userId, Pageable pageable);
}
