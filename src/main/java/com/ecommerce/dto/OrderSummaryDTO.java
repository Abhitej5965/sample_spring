package com.ecommerce.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class OrderSummaryDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private String orderStatus;
    private Double totalAmount;
    private String productNames;
    private String productPrices;
    private Integer totalProducts;
} 