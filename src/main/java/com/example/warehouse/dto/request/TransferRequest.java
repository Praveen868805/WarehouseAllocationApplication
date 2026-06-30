package com.example.warehouse.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferRequest {

    @NotNull
    private Long sourceWarehouseId;

    @NotNull
    private Long targetWarehouseId;

    @NotNull
    private Long productId;

    @NotNull
    @Min(value = 1)
    private Integer quantity;
}