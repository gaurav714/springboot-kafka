package com.springdemo.productservice.service;
import java.math.BigDecimal;
import java.util.List;

import com.springdemo.productservice.dto.ProductDto;
import com.springdemo.productservice.dto.ProductResponseDto;
import com.springdemo.productservice.model.Product;
import com.springdemo.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    public void createProduct(ProductDto productdto) {
    //first we have to map the ProductDto to Product
        Product product = Product.builder()
                .name(productdto.getName())
                .price(productdto.getPrice())
                .description(productdto.getDescription())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());


    }

    public List<ProductResponseDto> getAllProducts() {
        List<Product> all = productRepository.findAll();
        return all.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponseDto mapToProductResponse(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }
}
