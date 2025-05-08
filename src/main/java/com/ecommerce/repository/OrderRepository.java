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
    
    @Query(value = """
        SELECT 
            o.id as order_id,
            o.order_date,
            o.order_status,
            o.total_amount,
            GROUP_CONCAT(p.name SEPARATOR ', ') as product_names,
            GROUP_CONCAT(p.price SEPARATOR ', ') as product_prices,
            COUNT(p.id) as total_products
        FROM orders o
        JOIN order_products op ON o.id = op.order_id
        JOIN products p ON op.product_id = p.id
        WHERE o.id = :orderId
        GROUP BY o.id, o.order_date, o.order_status, o.total_amount
        """, nativeQuery = true)
    Object[] findOrderSummaryWithProductNames(@Param("orderId") Long orderId);
} 