package com.example.warehouse.mapper;

import com.example.warehouse.dto.reponse.ProductResponse;
import com.example.warehouse.dto.request.ProductRequest;
import com.example.warehouse.entity.Product;

public class ProductMapper {

    private ProductMapper() {
    }

    public static Product toEntity(ProductRequest request) {

        return Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .totalStock(request.getTotalStock())
                .build();
    }

    public static ProductResponse toResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .totalStock(product.getTotalStock())
                .build();
    }
}