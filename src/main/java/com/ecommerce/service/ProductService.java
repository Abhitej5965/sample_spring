package com.ecommerce.service;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public Product saveProduct(ProductDTO productDTO, MultipartFile file) throws IOException {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        
        if (file != null && !file.isEmpty()) {
            product.setProductMetadata(file.getBytes());
            product.setMetadataFileName(file.getOriginalFilename());
            product.setMetadataFileType(file.getContentType());
        }
        
        return productRepository.save(product);
    }
    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    public Product updateProduct(Long id, ProductDTO productDTO, MultipartFile file) throws IOException {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
            
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        
        if (file != null && !file.isEmpty()) {
            product.setProductMetadata(file.getBytes());
            product.setMetadataFileName(file.getOriginalFilename());
            product.setMetadataFileType(file.getContentType());
        }
        
        return productRepository.save(product);
    }
    
    public byte[] getProductMetadata(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        return product.getProductMetadata();
    }
} 