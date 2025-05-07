package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserService;
import com.ecommerce.dto.OrderedProductDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(user -> ResponseEntity.ok(orderService.getOrdersByUser(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Order> createOrder(
            @RequestPart("order") Order order,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            Order newOrder = orderService.createOrder(order, file);
            return ResponseEntity.ok(newOrder);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Order> updateOrder(
            @PathVariable Long id,
            @RequestPart("order") Order order,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            Order updatedOrder = orderService.updateOrder(id, order, file);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException | IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody String status) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> getOrderFile(@PathVariable Long id) {
        try {
            byte[] fileData = orderService.getOrderFile(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<OrderedProductDetailsDTO>> getOrderedProductsDetails(@PathVariable Long id) {
        try {
            List<OrderedProductDetailsDTO> products = orderService.getOrderedProductsDetails(id);
            return ResponseEntity.ok(products);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 