package com.example.warehouse.service.impl;

import com.example.warehouse.dto.reponse.*;
import com.example.warehouse.dto.request.*;
import com.example.warehouse.entity.*;
import com.example.warehouse.exception.*;
import com.example.warehouse.mapper.*;
import com.example.warehouse.repository.*;
import com.example.warehouse.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Override
    public WarehouseResponse createWarehouse(WarehouseRequest request) {

        if (warehouseRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(
                    "Warehouse already exists with name : " + request.getName());
        }

        Warehouse warehouse = WarehouseMapper.toEntity(request);

        Warehouse savedWarehouse = warehouseRepository.save(warehouse);

        return WarehouseMapper.toResponse(savedWarehouse);
    }

    @Override
    public WarehouseResponse updateWarehouse(Long id, WarehouseRequest request) {

        Warehouse warehouse = warehouseRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id : " + id));

        warehouse.setName(request.getName());
        warehouse.setLocation(request.getLocation());
        warehouse.setCapacity(request.getCapacity());

        Warehouse updatedWarehouse = warehouseRepository.save(warehouse);

        return WarehouseMapper.toResponse(updatedWarehouse);
    }

    @Override
    @Transactional(readOnly = true)
    public WarehouseResponse getWarehouseById(Long id) {

        Warehouse warehouse = warehouseRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id : " + id));

        return WarehouseMapper.toResponse(warehouse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseResponse> getAllWarehouses() {

        return warehouseRepository.findByDeletedFalse()
                .stream()
                .map(WarehouseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void activateWarehouse(Long id) {

        Warehouse warehouse = warehouseRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id : " + id));

        warehouse.setActive(true);

        warehouseRepository.save(warehouse);
    }

    @Override
    public void deactivateWarehouse(Long id) {

        Warehouse warehouse = warehouseRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id : " + id));

        warehouse.setActive(false);

        warehouseRepository.save(warehouse);
    }

    @Override
    public void deleteWarehouse(Long id) {

        Warehouse warehouse = warehouseRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Warehouse not found with id : " + id));

        warehouse.setDeleted(true);

        warehouseRepository.save(warehouse);
    }
}