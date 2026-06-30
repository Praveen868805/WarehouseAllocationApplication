package com.example.warehouse.service;



import java.util.List;

import com.example.warehouse.dto.reponse.InventoryResponse;
import com.example.warehouse.dto.request.InventoryRequest;

public interface InventoryService {

    InventoryResponse addInventory(InventoryRequest request);

    InventoryResponse updateInventory(InventoryRequest request);

    List<InventoryResponse> getAllInventory();
}