package com.example.warehouse.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequest {

    @NotNull
    private Long warehouseId;

    @NotNull
    private Long productId;

    @NotNull
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;
}