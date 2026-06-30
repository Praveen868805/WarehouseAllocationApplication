package com.example.warehouse.mapper;

import com.example.warehouse.dto.reponse.TransferResponse;
import com.example.warehouse.entity.StockTransfer;

public class TransferMapper {

    private TransferMapper() {
    }

    public static TransferResponse toResponse(StockTransfer transfer) {

        return TransferResponse.builder()
                .transferId(transfer.getId())
                .sourceWarehouseId(transfer.getSourceWarehouse().getId())
                .sourceWarehouse(transfer.getSourceWarehouse().getName())
                .targetWarehouseId(transfer.getTargetWarehouse().getId())
                .targetWarehouse(transfer.getTargetWarehouse().getName())
                .productId(transfer.getProduct().getId())
                .productName(transfer.getProduct().getName())
                .quantity(transfer.getQuantity())
                .transferDate(transfer.getTransferDate())
                .build();
    }
}