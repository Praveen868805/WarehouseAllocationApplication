package com.example.warehouse.dto.reponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private String sku;
    private Integer totalStock;
}