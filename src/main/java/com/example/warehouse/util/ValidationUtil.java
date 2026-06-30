package com.example.warehouse.util;

import com.example.warehouse.exception.CapacityExceededException;
import com.example.warehouse.exception.InsufficientStockException;

public class ValidationUtil {

    private ValidationUtil() {
    }

    // Validate Quantity
    public static void validateQuantity(Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero");
        }
    }

    // Validate Stock Availability
    public static void validateStock(Integer available,
                                     Integer requested) {

        if (requested > available) {

            throw new InsufficientStockException(
                    Constants.INSUFFICIENT_STOCK);
        }
    }

    // Validate Warehouse Capacity
    public static void validateCapacity(Integer capacity,
                                        Integer quantity) {

        if (quantity > capacity) {

            throw new CapacityExceededException(
                    Constants.CAPACITY_EXCEEDED);
        }
    }

    // Validate Non-Negative Stock
    public static void validateNonNegative(Integer quantity) {

        if (quantity < 0) {
            throw new IllegalArgumentException(
                    "Quantity cannot be negative");
        }
    }
}