package com.example.warehouse.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.warehouse.dto.reponse.ApiResponse;
import com.example.warehouse.dto.reponse.WarehouseResponse;
import com.example.warehouse.dto.request.WarehouseRequest;
import com.example.warehouse.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    // Create Warehouse
    @PostMapping
    public ResponseEntity<ApiResponse<WarehouseResponse>> createWarehouse(
            @Valid @RequestBody WarehouseRequest request) {

        WarehouseResponse response = warehouseService.createWarehouse(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<WarehouseResponse>builder()
                        .success(true)
                        .message("Warehouse created successfully")
                        .data(response)
                        .build());
    }

    // Get All Warehouses
    @GetMapping
    public ResponseEntity<ApiResponse<List<WarehouseResponse>>> getAllWarehouses() {

        List<WarehouseResponse> response = warehouseService.getAllWarehouses();

        return ResponseEntity.ok(
                ApiResponse.<List<WarehouseResponse>>builder()
                        .success(true)
                        .message("Warehouse list fetched successfully")
                        .data(response)
                        .build());
    }

    // Get Warehouse By Id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WarehouseResponse>> getWarehouseById(
            @PathVariable Long id) {

        WarehouseResponse response = warehouseService.getWarehouseById(id);

        return ResponseEntity.ok(
                ApiResponse.<WarehouseResponse>builder()
                        .success(true)
                        .message("Warehouse fetched successfully")
                        .data(response)
                        .build());
    }

    // Update Warehouse
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WarehouseResponse>> updateWarehouse(
            @PathVariable Long id,
            @Valid @RequestBody WarehouseRequest request) {

        WarehouseResponse response = warehouseService.updateWarehouse(id, request);

        return ResponseEntity.ok(
                ApiResponse.<WarehouseResponse>builder()
                        .success(true)
                        .message("Warehouse updated successfully")
                        .data(response)
                        .build());
    }

    // Activate Warehouse
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<String>> activateWarehouse(
            @PathVariable Long id) {

        warehouseService.activateWarehouse(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Warehouse activated successfully")
                        .data("ACTIVE")
                        .build());
    }

    // Deactivate Warehouse
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<String>> deactivateWarehouse(
            @PathVariable Long id) {

        warehouseService.deactivateWarehouse(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Warehouse deactivated successfully")
                        .data("INACTIVE")
                        .build());
    }

    // Soft Delete Warehouse
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteWarehouse(
            @PathVariable Long id) {

        warehouseService.deleteWarehouse(id);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Warehouse deleted successfully")
                        .data("DELETED")
                        .build());
    }
}