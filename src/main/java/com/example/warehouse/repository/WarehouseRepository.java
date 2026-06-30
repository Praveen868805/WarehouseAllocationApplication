package com.example.warehouse.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.warehouse.entity.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    Optional<Warehouse> findByName(String name);

    List<Warehouse> findByDeletedFalse();

    List<Warehouse> findByActiveTrue();

    Optional<Warehouse> findByIdAndDeletedFalse(Long id);

    boolean existsByName(String name);
}