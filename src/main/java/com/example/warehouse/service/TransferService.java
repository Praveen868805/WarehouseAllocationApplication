package com.example.warehouse.service;

import com.example.warehouse.dto.reponse.TransferResponse;
import com.example.warehouse.dto.request.TransferRequest;

public interface TransferService {

    TransferResponse transferStock(TransferRequest request);
}