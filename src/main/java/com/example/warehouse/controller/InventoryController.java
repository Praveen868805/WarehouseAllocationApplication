package com.example.warehouse.controller;

import com.example.warehouse.dto.reponse.ApiResponse;
import com.example.warehouse.dto.reponse.InventoryResponse;
import com.example.warehouse.dto.request.InventoryRequest;
import com.example.warehouse.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Add Inventory
     */
    @PostMapping
    public ResponseEntity<ApiResponse<InventoryResponse>> addInventory(
            @Valid @RequestBody InventoryRequest request) {

        InventoryResponse response = inventoryService.addInventory(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<InventoryResponse>builder()
                        .success(true)
                        .message("Inventory added successfully")
                        .data(response)
                        .build());
    }

    /**
     * Update Inventory
     */
    @PutMapping
    public ResponseEntity<ApiResponse<InventoryResponse>> updateInventory(
            @Valid @RequestBody InventoryRequest request) {

        InventoryResponse response = inventoryService.updateInventory(request);

        return ResponseEntity.ok(
                ApiResponse.<InventoryResponse>builder()
                        .success(true)
                        .message("Inventory updated successfully")
                        .data(response)
                        .build());
    }

    /**
     * Get All Inventory
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryResponse>>> getAllInventory() {

        List<InventoryResponse> response = inventoryService.getAllInventory();

        return ResponseEntity.ok(
                ApiResponse.<List<InventoryResponse>>builder()
                        .success(true)
                        .message("Inventory fetched successfully")
                        .data(response)
                        .build());
    }

}