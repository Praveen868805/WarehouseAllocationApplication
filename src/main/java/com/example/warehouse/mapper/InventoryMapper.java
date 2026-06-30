package com.example.warehouse.mapper;

import com.example.warehouse.dto.reponse.InventoryResponse;
import com.example.warehouse.entity.WarehouseInventory;

public class InventoryMapper {

    private InventoryMapper() {
    }

    public static InventoryResponse toResponse(WarehouseInventory inventory) {

        return InventoryResponse.builder()
                .warehouseId(inventory.getWarehouse().getId())
                .warehouseName(inventory.getWarehouse().getName())
                .productId(inventory.getProduct().getId())
                .productName(inventory.getProduct().getName())
                .availableQuantity(inventory.getAvailableQuantity())
                .build();
    }
}