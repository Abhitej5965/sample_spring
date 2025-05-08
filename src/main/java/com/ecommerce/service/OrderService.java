package com.ecommerce.service;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.dto.OrderedProductDetailsDTO;
import com.ecommerce.dto.OrderSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }
    
    public Order createOrder(Order order, MultipartFile file) throws IOException {
        order.setOrderDate(LocalDateTime.now());
        if (file != null && !file.isEmpty()) {
            order.setFileData(file.getBytes());
        }
        return orderRepository.save(order);
    }
    
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    
    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
            
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }
    
    public Order updateOrder(Long id, Order orderDetails, MultipartFile file) throws IOException {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
            
        order.setTotalAmount(orderDetails.getTotalAmount());
        order.setOrderStatus(orderDetails.getOrderStatus());
        order.setShippingAddress(orderDetails.getShippingAddress());
        
        if (file != null && !file.isEmpty()) {
            order.setFileData(file.getBytes());
        }
        
        return orderRepository.save(order);
    }
    
    public byte[] getOrderFile(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        return order.getFileData();
    }
    
    public List<OrderedProductDetailsDTO> getOrderedProductsDetails(Long orderId) {
        List<Object[]> results = orderRepository.findOrderedProductsDetails(orderId);
        return results.stream()
            .map(row -> {
                OrderedProductDetailsDTO dto = new OrderedProductDetailsDTO();
                dto.setProductId(((Number) row[0]).longValue());
                dto.setProductName((String) row[1]);
                dto.setDescription((String) row[2]);
                dto.setPrice(((Number) row[3]).doubleValue());
                dto.setStockQuantity(((Number) row[4]).intValue());
                dto.setOrderDate(((java.sql.Timestamp) row[5]).toLocalDateTime());
                dto.setOrderStatus((String) row[6]);
                dto.setTotalAmount(((Number) row[7]).doubleValue());
                return dto;
            })
            .collect(Collectors.toList());
    }
    
    public OrderSummaryDTO getOrderSummaryWithProductNames(Long orderId) {
        Object[] result = orderRepository.findOrderSummaryWithProductNames(orderId);
        if (result == null || result.length == 0) {
            throw new RuntimeException("Order not found");
        }
        
        // Get the inner array which contains the actual data
        Object[] dataArray = (Object[]) result[0];
        
        OrderSummaryDTO dto = new OrderSummaryDTO();
        dto.setOrderId(((Number) dataArray[0]).longValue());
        dto.setOrderDate(((java.sql.Timestamp) dataArray[1]).toLocalDateTime());
        dto.setOrderStatus((String) dataArray[2]);
        dto.setTotalAmount(((Number) dataArray[3]).doubleValue());
        dto.setProductNames((String) dataArray[4]);
        dto.setProductPrices((String) dataArray[5]);
        dto.setTotalProducts(((Number) dataArray[6]).intValue());
        
        return dto;
    }
} 