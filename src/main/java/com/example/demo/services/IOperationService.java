package com.example.demo.services;

import com.example.demo.entities.OperationModel;

import java.util.List;

public interface IOperationService {

    List<OperationModel>findAll();
    OperationModel findById(Long id);
    void save(OperationModel operationModel);
    void delete(Long id);
}
