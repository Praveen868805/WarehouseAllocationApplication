package com.example.warehouse.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.warehouse.entity.Product;
import com.example.warehouse.entity.Warehouse;
import com.example.warehouse.entity.WarehouseInventory;

import java.util.List;
import java.util.Optional;

public interface WarehouseInventoryRepository
        extends JpaRepository<WarehouseInventory, Long> {

    Optional<WarehouseInventory> findByWarehouseAndProduct(
            Warehouse warehouse,
            Product product);

    List<WarehouseInventory> findByProduct(Product product);

    List<WarehouseInventory> findByWarehouse(Warehouse warehouse);

    List<WarehouseInventory> findByProductOrderByAvailableQuantityDesc(Product product);
}