package com.ecommerce.service;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
} 