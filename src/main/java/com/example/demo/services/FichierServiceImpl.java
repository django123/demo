package com.example.demo.services;

import com.example.demo.entities.*;
import com.example.demo.repositories.FichierRepository;
import com.example.demo.repositories.OperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class FichierServiceImpl implements IFichierService{
    private final FichierRepository fichierRepository;

    private final OperationRepository operationRepository;

    public FichierServiceImpl(FichierRepository fichierRepository, OperationRepository operationRepository) {
        this.fichierRepository = fichierRepository;
        this.operationRepository = operationRepository;
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

    public FichierStatistiquesModel getFichierStatistics(Long fichierId) {
        FichierModel fichier = fichierRepository.findById(fichierId).orElse(null);
        FichierStatistiquesModel statistiques = new FichierStatistiquesModel();
        statistiques.setTotal(fichier.getNbrOperations());
        statistiques.setNotTreated(0);

        for (OperationModel operation : fichier.getOperations()) {
            if (!operation.getStatut().equals(OperationStatusEnum.TREATED)) {
                statistiques.setNotTreated(statistiques.getNotTreated() + 1);
            }
        }

        List<TypeImportEnum> typesImport = Arrays.asList(TypeImportEnum.values());
        for (TypeImportEnum typeImport : typesImport) {
            List<OperationModel> operations = operationRepository.findByTypeImportAndFichierId(typeImport, fichierId);
            if (operations.size() > 0) {
                List<OperationModel> nonTreatedOperations = new ArrayList<>();
                for (OperationModel operation : operations) {
                    if (!operation.getStatut().equals(OperationStatusEnum.TREATED)) {
                        nonTreatedOperations.add(operation);
                    }
                }
            }

            if (operations.size() > 0) {
                ImportStatDto importStatistiques = new ImportStatDto();
                importStatistiques.setLibelle("test");
                importStatistiques.setTypeImport(typeImport);
                importStatistiques.setTotalImport(operations.size());

                statistiques.getImportStatDtos().add(importStatistiques);
            }
        }

        return statistiques;
    }

    private List<OperationModel> findByNonTreatedStatut(List<OperationModel> operations) {
        List<OperationModel> nonTreatedOperations = new ArrayList<>();
        for (OperationModel operation : operations) {
            if (!operation.getStatut().equals(OperationStatusEnum.TREATED)) {
                nonTreatedOperations.add(operation);
            }
        }
        return nonTreatedOperations;
    }

}







