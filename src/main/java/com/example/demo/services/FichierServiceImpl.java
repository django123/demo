package com.example.demo.services;

import com.example.demo.entities.*;
import com.example.demo.repositories.FichierRepository;
import com.example.demo.repositories.OperationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

        // Récupérer toutes les opérations liées au fichier
        List<OperationModel> operations = fichier.getOperations();

        // Compter le nombre total d'opérations du fichier
        statistiques.setTotal(operations.size());

        // Compter le nombre d'opérations non traitées
        long notTreatedCount = operations.stream()
                .filter(operation -> !operation.getStatut().equals(OperationStatusEnum.TREATED))
                .count();
        statistiques.setNotTreated(notTreatedCount);

        // Regrouper les opérations par type d'import et calculer les statistiques pour chaque groupe
        Map<TypeImportEnum, List<OperationModel>> operationsByType = operations.stream()
                .filter(operation -> !operation.getStatut().equals(OperationStatusEnum.TREATED))
                .collect(Collectors.groupingBy(OperationModel::getTypeImport));
        List<ImportStatDto> importStats = operationsByType.entrySet().stream()
                .map(entry -> {
                    TypeImportEnum typeImport = entry.getKey();
                    List<OperationModel> typeOperations = entry.getValue();

                    ImportStatDto importStat = new ImportStatDto(typeImport);
                    importStat.setTotalImport(typeOperations.size());
                    return importStat;
                })
                .collect(Collectors.toList());

        // Mettre à jour les statistiques globales avec les statistiques par type d'import
        statistiques.getImportStatDtos().addAll(importStats);

        return statistiques;
    }

    @Override
    public Map<String, Object> dorisFichierStatistics(Long fichierId) {
        FichierModel fichier = fichierRepository.findById(fichierId)
                .orElseThrow(() -> new EntityNotFoundException("Fichier not found"));

        // Retrieve all operations with status other than TREATED for the given fichier
        List<OperationModel> operations = operationRepository.findByFichierAndStatutNot(fichier, OperationStatusEnum.TREATED);

        // Calculate the statistics
        long total = operations.size();
        long notTreated = operations.stream()
                .filter(operation -> operation.getStatut() != OperationStatusEnum.TREATED)
                .count();
        Map<String, Long> imports = new HashMap<>();
        for (OperationModel op : operations){
            String typeImport = op.getTypeImport().toString();
            imports.put(typeImport, imports.getOrDefault(typeImport,0L) +1L);
        }

        // Build the response JSON
        Map<String, Object> response = new HashMap<>();
        response.put("total", total);
        response.put("notTreated", notTreated);
        List<Map<String, Object>> importsList = new ArrayList<>();
        imports.forEach((typeImport, count) -> {
            Map<String, Object> importMap = new HashMap<>();
            importMap.put("libelle", typeImport.toString());
            importMap.put("typeImport", typeImport.toString());
            importMap.put("totalImport", count);
            importsList.add(importMap);
        });
        response.put("imports", importsList);

        return response;
    }

}




/*@Service
@Transactional
public class FichierServiceImpl implements IFichierService {

    private final FichierRepository fichierRepository;
    private final OperationRepository operationRepository;

    public FichierServiceImpl(FichierRepository fichierRepository, OperationRepository operationRepository) {
        this.fichierRepository = fichierRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public FichierStatistiquesModel getFichierStatistics(Long fichierId) {
        FichierModel fichier = fichierRepository.findById(fichierId).orElse(null);
        FichierStatistiquesModel statistiques = new FichierStatistiquesModel();
        statistiques.setTotal(fichier.getNbrOperations());
        statistiques.setNotTreated(0);
        List<OperationModel> operations = fichier.getOperations();
        long notTreatedCount = operations.stream()
                .filter(operation -> !operation.getStatut().equals(OperationStatusEnum.TREATED))
                .count();
        statistiques.setNotTreated(notTreatedCount);

        List<TypeImportEnum> typesImport = Arrays.asList(TypeImportEnum.values());
        typesImport.stream()
                .forEach(typeImport -> {
                    List<OperationModel> typeImportOperations = operationRepository.findByTypeImportAndFichierId(typeImport, fichierId);
                    if (typeImportOperations.size() > 0) {
                        List<OperationModel> nonTreatedOperations = typeImportOperations.stream()
                                .filter(operation -> !operation.getStatut().equals(OperationStatusEnum.TREATED))
                                .collect(Collectors.toList());
                        if (nonTreatedOperations.size() > 0) {
                            ImportStatDto importStatistiques = new ImportStatDto();
                            importStatistiques.setLibelle("");
                            importStatistiques.setTypeImport(typeImport);
                            importStatistiques.setTotalImport(nonTreatedOperations.size());

                            statistiques.getImportStatDtos().add(importStatistiques);
                        }
                    }
                });

        return statistiques;


*//*        for (OperationModel operation : fichier.getOperations()) {
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
                if (nonTreatedOperations.size() > 0) {
                    ImportStatDto importStatistiques = new ImportStatDto();
                    importStatistiques.setLibelle("");
                    importStatistiques.setTypeImport(typeImport);
                    importStatistiques.setTotalImport(nonTreatedOperations.size());

                    statistiques.getImportStatDtos().add(importStatistiques);
                }
            }
        }

        return statistiques;*//*
    }
}*/







