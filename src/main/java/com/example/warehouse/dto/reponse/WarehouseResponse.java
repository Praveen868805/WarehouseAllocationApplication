package com.example.warehouse.dto.reponse;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WarehouseResponse {

    private Long id;
    private String name;
    private String location;
    private Integer capacity;
    private Boolean active;
    private LocalDateTime createdAt;
}