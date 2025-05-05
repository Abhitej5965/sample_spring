package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    
    @Lob
    @Column(name = "product_metadata")
    private byte[] productMetadata;
    
    @Column(name = "metadata_file_name")
    private String metadataFileName;
    
    @Column(name = "metadata_file_type")
    private String metadataFileType;
} 