package com.example.warehouse.dto.reponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryResponse {

    private Long warehouseId;
    private String warehouseName;

    private Long productId;
    private String productName;

    private Integer availableQuantity;
}