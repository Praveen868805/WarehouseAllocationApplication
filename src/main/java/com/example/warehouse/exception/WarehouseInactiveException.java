package com.example.warehouse.exception;

public class WarehouseInactiveException extends RuntimeException {

    public WarehouseInactiveException(String message) {
        super(message);
    }
}