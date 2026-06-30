package com.example.warehouse.service;


import java.util.List;

import com.example.warehouse.dto.reponse.ProductResponse;
import com.example.warehouse.dto.request.ProductRequest;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(Long id, ProductRequest request);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();
}