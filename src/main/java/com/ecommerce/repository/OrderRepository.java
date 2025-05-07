package com.ecommerce.repository;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    
    @Query(value = """
        SELECT p.id, p.name, p.description, p.price, p.stock_quantity, 
               o.order_date, o.order_status, o.total_amount
        FROM products p
        JOIN order_products op ON p.id = op.product_id
        JOIN orders o ON op.order_id = o.id
        WHERE o.id = :orderId
        """, nativeQuery = true)
    List<Object[]> findOrderedProductsDetails(@Param("orderId") Long orderId);
} 