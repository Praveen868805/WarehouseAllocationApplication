package com.example.warehouse.service.impl;

import com.example.warehouse.dto.reponse.*;
import com.example.warehouse.dto.request.*;
import com.example.warehouse.entity.*;
import com.example.warehouse.exception.*;
import com.example.warehouse.mapper.*;
import com.example.warehouse.repository.*;
import com.example.warehouse.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;
    private final WarehouseInventoryRepository inventoryRepository;

    @Override
    public InventoryResponse addInventory(InventoryRequest request) {

        Warehouse warehouse = warehouseRepository
                .findByIdAndDeletedFalse(request.getWarehouseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found"));

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        if (request.getQuantity() > warehouse.getCapacity()) {
            throw new CapacityExceededException(
                    "Warehouse capacity exceeded");
        }

        WarehouseInventory inventory = inventoryRepository
                .findByWarehouseAndProduct(warehouse, product)
                .orElse(new WarehouseInventory());

        inventory.setWarehouse(warehouse);
        inventory.setProduct(product);
        inventory.setAvailableQuantity(request.getQuantity());

        WarehouseInventory saved =
                inventoryRepository.save(inventory);

        return InventoryMapper.toResponse(saved);
    }

    @Override
    public InventoryResponse updateInventory(InventoryRequest request) {

        Warehouse warehouse = warehouseRepository
                .findByIdAndDeletedFalse(request.getWarehouseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found"));

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found"));

        WarehouseInventory inventory = inventoryRepository
                .findByWarehouseAndProduct(warehouse, product)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory not found"));

        if (request.getQuantity() > warehouse.getCapacity()) {
            throw new CapacityExceededException(
                    "Warehouse capacity exceeded");
        }

        inventory.setAvailableQuantity(request.getQuantity());

        WarehouseInventory updated =
                inventoryRepository.save(inventory);

        return InventoryMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getAllInventory() {

        return inventoryRepository.findAll()
                .stream()
                .map(InventoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}