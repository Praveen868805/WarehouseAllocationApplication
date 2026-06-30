package com.example.warehouse.service.impl;

import com.example.warehouse.dto.reponse.TransferResponse;
import com.example.warehouse.dto.request.TransferRequest;
import com.example.warehouse.entity.Product;
import com.example.warehouse.entity.StockTransfer;
import com.example.warehouse.entity.Warehouse;
import com.example.warehouse.entity.WarehouseInventory;
import com.example.warehouse.exception.CapacityExceededException;
import com.example.warehouse.exception.InsufficientStockException;
import com.example.warehouse.exception.ResourceNotFoundException;
import com.example.warehouse.exception.WarehouseInactiveException;
import com.example.warehouse.mapper.TransferMapper;
import com.example.warehouse.repository.ProductRepository;
import com.example.warehouse.repository.StockTransferRepository;
import com.example.warehouse.repository.WarehouseInventoryRepository;
import com.example.warehouse.repository.WarehouseRepository;
import com.example.warehouse.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TransferServiceImpl implements TransferService {

    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final WarehouseInventoryRepository inventoryRepository;
    private final StockTransferRepository transferRepository;

    @Override
    public TransferResponse transferStock(TransferRequest request) {

        Warehouse sourceWarehouse = warehouseRepository
                .findByIdAndDeletedFalse(request.getSourceWarehouseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Source warehouse not found"));

        Warehouse targetWarehouse = warehouseRepository
                .findByIdAndDeletedFalse(request.getTargetWarehouseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Target warehouse not found"));

        if (sourceWarehouse.getId().equals(targetWarehouse.getId())) {
            throw new IllegalArgumentException(
                    "Source and Target warehouse cannot be the same.");
        }

        if (!Boolean.TRUE.equals(sourceWarehouse.getActive())) {
            throw new WarehouseInactiveException(
                    "Source warehouse is inactive.");
        }

        if (!Boolean.TRUE.equals(targetWarehouse.getActive())) {
            throw new WarehouseInactiveException(
                    "Target warehouse is inactive.");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        WarehouseInventory sourceInventory = inventoryRepository
                .findByWarehouseAndProduct(sourceWarehouse, product)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not available in source warehouse"));

        WarehouseInventory targetInventory = inventoryRepository
                .findByWarehouseAndProduct(targetWarehouse, product)
                .orElseGet(() -> {

                    WarehouseInventory inventory = new WarehouseInventory();
                    inventory.setWarehouse(targetWarehouse);
                    inventory.setProduct(product);
                    inventory.setAvailableQuantity(0);

                    return inventory;
                });

        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero.");
        }

        if (sourceInventory.getAvailableQuantity() < request.getQuantity()) {
            throw new InsufficientStockException(
                    "Insufficient stock in source warehouse.");
        }

        if (targetInventory.getAvailableQuantity() + request.getQuantity()
                > targetWarehouse.getCapacity()) {

            throw new CapacityExceededException(
                    "Target warehouse capacity exceeded.");
        }

        sourceInventory.setAvailableQuantity(
                sourceInventory.getAvailableQuantity()
                        - request.getQuantity());

        targetInventory.setAvailableQuantity(
                targetInventory.getAvailableQuantity()
                        + request.getQuantity());

        inventoryRepository.save(sourceInventory);
        inventoryRepository.save(targetInventory);

        StockTransfer transfer = StockTransfer.builder()
                .sourceWarehouse(sourceWarehouse)
                .targetWarehouse(targetWarehouse)
                .product(product)
                .quantity(request.getQuantity())
                .transferDate(LocalDateTime.now())
                .build();

        StockTransfer savedTransfer = transferRepository.save(transfer);

        return TransferMapper.toResponse(savedTransfer);
    }
}