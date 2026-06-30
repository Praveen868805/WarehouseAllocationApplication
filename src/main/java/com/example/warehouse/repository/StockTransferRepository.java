package com.example.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.warehouse.entity.StockTransfer;

public interface StockTransferRepository
        extends JpaRepository<StockTransfer, Long> {

}