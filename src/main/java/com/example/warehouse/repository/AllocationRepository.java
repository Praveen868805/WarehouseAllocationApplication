package com.example.warehouse.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.warehouse.entity.Allocation;
import com.example.warehouse.entity.Product;
import com.example.warehouse.entity.Warehouse;

import java.time.LocalDateTime;

public interface AllocationRepository extends JpaRepository<Allocation, Long> {

    Page<Allocation> findByProduct(Product product, Pageable pageable);

    Page<Allocation> findByWarehouse(Warehouse warehouse, Pageable pageable);

    Page<Allocation> findByAllocatedAtBetween(
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);

    Page<Allocation> findByProductAndWarehouse(
            Product product,
            Warehouse warehouse,
            Pageable pageable);

    Page<Allocation> findByProductAndAllocatedAtBetween(
            Product product,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);

    Page<Allocation> findByWarehouseAndAllocatedAtBetween(
            Warehouse warehouse,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);
}