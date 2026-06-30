package com.example.warehouse.service;



import java.util.List;

import com.example.warehouse.dto.reponse.WarehouseResponse;
import com.example.warehouse.dto.request.WarehouseRequest;

public interface WarehouseService {

    WarehouseResponse createWarehouse(WarehouseRequest request);

    WarehouseResponse updateWarehouse(Long id, WarehouseRequest request);

    WarehouseResponse getWarehouseById(Long id);

    List<WarehouseResponse> getAllWarehouses();

    void activateWarehouse(Long id);

    void deactivateWarehouse(Long id);

    void deleteWarehouse(Long id);
}