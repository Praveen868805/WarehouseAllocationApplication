package com.example.warehouse.dto.reponse;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AllocationResponse {

    private Long allocationId;

    private Long warehouseId;
    private String warehouseName;

    private Long productId;
    private String productName;

    private Integer quantity;

    private String status;

    private LocalDateTime allocatedAt;
}