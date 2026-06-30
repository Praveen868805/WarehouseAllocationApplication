package com.example.warehouse.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.warehouse.dto.reponse.AllocationResponse;
import com.example.warehouse.dto.reponse.ApiResponse;
import com.example.warehouse.dto.request.AllocationRequest;
import com.example.warehouse.service.AllocationService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/allocations")
@RequiredArgsConstructor
public class AllocationController {

    private final AllocationService allocationService;

    @PostMapping
    public ResponseEntity<ApiResponse<AllocationResponse>> allocateProduct(
            @Valid @RequestBody AllocationRequest request) {

        AllocationResponse response = allocationService.allocateProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<AllocationResponse>builder()
                        .success(true)
                        .message("Product allocated successfully")
                        .data(response)
                        .build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<AllocationResponse>>> searchAllocations(

            @RequestParam(required = false) Long productId,

            @RequestParam(required = false) Long warehouseId,

            @RequestParam(required = false)
            LocalDateTime from,

            @RequestParam(required = false)
            LocalDateTime to,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "allocatedAt") String sortBy,

            @RequestParam(defaultValue = "desc") String direction) {

        Page<AllocationResponse> response =
                allocationService.searchAllocations(
                        productId,
                        warehouseId,
                        from,
                        to,
                        page,
                        size,
                        sortBy,
                        direction);

        return ResponseEntity.ok(
                ApiResponse.<Page<AllocationResponse>>builder()
                        .success(true)
                        .message("Allocation history fetched successfully")
                        .data(response)
                        .build());
    }
}