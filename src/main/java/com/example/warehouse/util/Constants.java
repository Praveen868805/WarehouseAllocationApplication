package com.example.warehouse.util;

public final class Constants {

    private Constants() {
    }

    // Warehouse Status
    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";

    // Allocation Status
    public static final String ALLOCATED = "ALLOCATED";
    public static final String CANCELLED = "CANCELLED";

    // Transfer Status
    public static final String TRANSFERRED = "TRANSFERRED";
    public static final String FAILED = "FAILED";

    // Messages
    public static final String PRODUCT_NOT_FOUND = "Product not found";

    public static final String WAREHOUSE_NOT_FOUND = "Warehouse not found";

    public static final String INVENTORY_NOT_FOUND = "Inventory not found";

    public static final String INSUFFICIENT_STOCK = "Insufficient stock available";

    public static final String CAPACITY_EXCEEDED = "Warehouse capacity exceeded";
}