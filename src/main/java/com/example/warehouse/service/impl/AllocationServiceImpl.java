
package com.example.warehouse.service.impl;

import com.example.warehouse.dto.reponse.AllocationResponse;
import com.example.warehouse.dto.request.AllocationRequest;
import com.example.warehouse.entity.Allocation;
import com.example.warehouse.entity.Product;
import com.example.warehouse.entity.Warehouse;
import com.example.warehouse.entity.WarehouseInventory;
import com.example.warehouse.exception.InsufficientStockException;
import com.example.warehouse.exception.ResourceNotFoundException;
import com.example.warehouse.mapper.AllocationMapper;
import com.example.warehouse.repository.AllocationRepository;
import com.example.warehouse.repository.ProductRepository;
import com.example.warehouse.repository.WarehouseInventoryRepository;
import com.example.warehouse.repository.WarehouseRepository;
import com.example.warehouse.service.AllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AllocationServiceImpl implements AllocationService {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseInventoryRepository inventoryRepository;
    private final AllocationRepository allocationRepository;

    @Override
    public AllocationResponse allocateProduct(AllocationRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        List<WarehouseInventory> inventories =
                inventoryRepository.findByProductOrderByAvailableQuantityDesc(product);

        if (inventories.isEmpty()) {
            throw new InsufficientStockException(
                    "No warehouse contains this product.");
        }

        WarehouseInventory selectedInventory = null;

        for (WarehouseInventory inventory : inventories) {

            if (!inventory.getWarehouse().getActive()) {
                continue;
            }

            if (inventory.getAvailableQuantity() >= request.getQuantity()) {
                selectedInventory = inventory;
                break;
            }
        }

        if (selectedInventory == null) {
            throw new InsufficientStockException(
                    "Insufficient stock available.");
        }

        try {

            selectedInventory.setAvailableQuantity(
                    selectedInventory.getAvailableQuantity()
                            - request.getQuantity());

            inventoryRepository.save(selectedInventory);

        } catch (OptimisticLockingFailureException ex) {

            throw new RuntimeException(
                    "Inventory was modified by another transaction. Please retry.");
        }

        Allocation allocation = Allocation.builder()
                .warehouse(selectedInventory.getWarehouse())
                .product(product)
                .quantity(request.getQuantity())
                .status("ALLOCATED")
                .allocatedAt(LocalDateTime.now())
                .build();

        Allocation saved = allocationRepository.save(allocation);

        return AllocationMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllocationResponse> searchAllocations(
            Long productId,
            Long warehouseId,
            LocalDateTime from,
            LocalDateTime to,
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Allocation> allocations;

        if (productId != null && warehouseId != null) {

            Product product = productRepository.findById(productId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Product not found"));

            Warehouse warehouse = warehouseRepository
                    .findByIdAndDeletedFalse(warehouseId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Warehouse not found"));

            allocations = allocationRepository.findByProductAndWarehouse(
                    product,
                    warehouse,
                    pageable);

        } else if (productId != null) {

            Product product = productRepository.findById(productId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Product not found"));

            allocations = allocationRepository.findByProduct(
                    product,
                    pageable);

        } else if (warehouseId != null) {

            Warehouse warehouse = warehouseRepository
                    .findByIdAndDeletedFalse(warehouseId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Warehouse not found"));

            allocations = allocationRepository.findByWarehouse(
                    warehouse,
                    pageable);

        } else if (from != null && to != null) {

            allocations = allocationRepository.findByAllocatedAtBetween(
                    from,
                    to,
                    pageable);

        } else {

            allocations = allocationRepository.findAll(pageable);
        }

        return allocations.map(AllocationMapper::toResponse);
    }
}