package com.example.warehouse.mapper;

import com.example.warehouse.dto.reponse.WarehouseResponse;
import com.example.warehouse.dto.request.WarehouseRequest;
import com.example.warehouse.entity.Warehouse;

public class WarehouseMapper {

    private WarehouseMapper() {
    }

    public static Warehouse toEntity(WarehouseRequest request) {

        return Warehouse.builder()
                .name(request.getName())
                .location(request.getLocation())
                .capacity(request.getCapacity())
                .active(true)
                .deleted(false)
                .build();
    }

    public static WarehouseResponse toResponse(Warehouse warehouse) {

        return WarehouseResponse.builder()
                .id(warehouse.getId())
                .name(warehouse.getName())
                .location(warehouse.getLocation())
                .capacity(warehouse.getCapacity())
                .active(warehouse.getActive())
                .createdAt(warehouse.getCreatedAt())
                .build();
    }
}