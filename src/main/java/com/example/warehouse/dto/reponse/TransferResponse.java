package com.example.warehouse.dto.reponse;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransferResponse {

    private Long transferId;

    private Long sourceWarehouseId;
    private String sourceWarehouse;

    private Long targetWarehouseId;
    private String targetWarehouse;

    private Long productId;
    private String productName;

    private Integer quantity;

    private LocalDateTime transferDate;
}