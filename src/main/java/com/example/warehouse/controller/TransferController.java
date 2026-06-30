package com.example.warehouse.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.warehouse.dto.reponse.ApiResponse;
import com.example.warehouse.dto.reponse.TransferResponse;
import com.example.warehouse.dto.request.TransferRequest;
import com.example.warehouse.service.TransferService;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    // Transfer Stock
    @PostMapping
    public ResponseEntity<ApiResponse<TransferResponse>> transferStock(
            @Valid @RequestBody TransferRequest request) {

        TransferResponse response =
                transferService.transferStock(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TransferResponse>builder()
                        .success(true)
                        .message("Stock transferred successfully")
                        .data(response)
                        .build());
    }
}