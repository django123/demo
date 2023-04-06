package com.example.demo.services;

import com.example.demo.entities.*;
import com.example.demo.repositories.FichierRepository;
import com.example.demo.repositories.IConfigImportRepository;
import com.example.demo.repositories.OperationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class FichierServiceImpl implements IFichierService{
    private final FichierRepository fichierRepository;

    private final OperationRepository operationRepository;

    private final IConfigImportRepository configImportRepository;

    public FichierServiceImpl(FichierRepository fichierRepository, OperationRepository operationRepository, IConfigImportRepository configImportRepository) {
        this.fichierRepository = fichierRepository;
        this.operationRepository = operationRepository;
        this.configImportRepository = configImportRepository;
    }

    @Override
    public void save(FichierModel fichierModel) {
        fichierRepository.save(fichierModel);
    }

    @Override
    public List<FichierModel> findAll() {
        return fichierRepository.findAll();
    }

    @Override
    public FichierModel findById(Long id) {
        return fichierRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
       fichierRepository.deleteById(id);
    }


    @Override
    public Map<String, Object> dorisFichierStatistics(Long fichierId) {
        FichierModel fichier = fichierRepository.findById(fichierId)
                .orElseThrow(() -> new EntityNotFoundException("Fichier not found"));

        // Retrieve all operations with status other than TREATED for the given fichier
        List<OperationModel> operations = operationRepository.findByFichier_IdAndStatutNot(fichier.getId(), OperationStatusEnum.TREATED);

        // Calculate the statistics
        long total = operations.size();
        long notTreated = operations.stream()
                .filter(operation -> operation.getStatut() != OperationStatusEnum.TREATED)
                .count();
        Map<TypeImportEnum, Long> imports = new HashMap<>();
        for (OperationModel op : operations){
            imports.put(op.getTypeImport(), imports.getOrDefault(op.getTypeImport(),0L) +1L);
        }

        // Build the response JSON
        Map<String, Object> response = new HashMap<>();
        response.put("total", total);
        response.put("notTreated", notTreated);
        List<Map<String, Object>> importsList = new ArrayList<>();
        imports.forEach((typeImport, count) -> {
            ConfigImportModel configImportModel = configImportRepository.findByTypeImportEnum(typeImport);
            String operationName = configImportModel !=null ? configImportModel.getOperation() : "";
            Map<String, Object> importMap = new HashMap<>();
            importMap.put("libelle", operationName);
            importMap.put("typeImport", typeImport);
            importMap.put("totalImport", count);
            importsList.add(importMap);
        });
        response.put("imports", importsList);

        return response;
    }

}








