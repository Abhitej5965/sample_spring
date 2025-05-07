package com.ecommerce.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderedProductDetailsDTO {
    private Long productId;
    private String productName;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private LocalDateTime orderDate;
    private String orderStatus;
    private Double totalAmount;
} 