package com.example.demo.repositories;

import org.springframework.stereotype.Repository;

@Repository
public class ImportRepositoryImp {

    private final OperationRepository operationRepository;

    public ImportRepositoryImp(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public void updateOperationStatutCarteId(Long carteId){
        operationRepository.updateOperationStatusCarteId(carteId);
    }
}
