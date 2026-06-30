package com.example.warehouse.mapper;

import com.example.warehouse.dto.reponse.AllocationResponse;
import com.example.warehouse.entity.Allocation;

public class AllocationMapper {

    private AllocationMapper() {
    }

    public static AllocationResponse toResponse(Allocation allocation) {

        return AllocationResponse.builder()
                .allocationId(allocation.getId())
                .warehouseId(allocation.getWarehouse().getId())
                .warehouseName(allocation.getWarehouse().getName())
                .productId(allocation.getProduct().getId())
                .productName(allocation.getProduct().getName())
                .quantity(allocation.getQuantity())
                .status(allocation.getStatus())
                .allocatedAt(allocation.getAllocatedAt())
                .build();
    }
}