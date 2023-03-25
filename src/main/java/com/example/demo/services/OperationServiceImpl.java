package com.example.demo.services;

import com.example.demo.entities.OperationModel;
import com.example.demo.repositories.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OperationServiceImpl implements IOperationService{

    private final OperationRepository operationRepository;

    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public List<OperationModel> findAll() {
        return operationRepository.findAll();
    }

    @Override
    public OperationModel findById(Long id) {
        return operationRepository.findById(id).orElse(null);
    }

    @Override
    public void save(OperationModel operationModel) {
       operationRepository.save(operationModel);
    }

    @Override
    public void delete(Long id) {
        operationRepository.deleteById(id);
    }
}
