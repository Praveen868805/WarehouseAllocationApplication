package com.example.warehouse.service;


import org.springframework.data.domain.Page;

import com.example.warehouse.dto.reponse.AllocationResponse;
import com.example.warehouse.dto.request.AllocationRequest;

import java.time.LocalDateTime;

public interface AllocationService {

    AllocationResponse allocateProduct(AllocationRequest request);

    Page<AllocationResponse> searchAllocations(
            Long productId,
            Long warehouseId,
            LocalDateTime from,
            LocalDateTime to,
            int page,
            int size,
            String sortBy,
            String direction);
}