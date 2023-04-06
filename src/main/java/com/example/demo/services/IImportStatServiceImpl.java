package com.example.demo.services;

import com.example.demo.entities.ConfigImportModel;
import com.example.demo.entities.ImportModel;
import com.example.demo.entities.ImportStatDto;
import com.example.demo.entities.TypeImportEnum;
import com.example.demo.repositories.IConfigImportRepository;
import com.example.demo.repositories.IImportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IImportStatServiceImpl implements IImportStatService{

    private final IImportRepository importRepository;

    private final IConfigImportRepository configImportRepository;

    public IImportStatServiceImpl(IImportRepository importRepository, IConfigImportRepository configImportRepository) {
        this.importRepository = importRepository;
        this.configImportRepository = configImportRepository;
    }

    @Override
    public List<ImportStatDto> getImportStats(String typeImport) {
        List<ImportModel> importModels;
        if (typeImport != null) {
            importModels = importRepository.findByTypeImportAndUserDownloadNotNull(TypeImportEnum.valueOf(typeImport));
        } else {
            importModels = importRepository.findByUserDownloadNotNull();
        }

        Map<TypeImportEnum, ConfigImportModel> configImportMap = configImportRepository.findAll()
                .stream()
                .collect(Collectors.toMap(ConfigImportModel::getTypeImportEnum, Function.identity()));

        Map<TypeImportEnum, ImportStatDto> importStats = new HashMap<>();

        for (ImportModel importModel : importModels){
            TypeImportEnum typeImportEnum = importModel.getTypeImport();
            if (!importStats.containsKey(typeImportEnum)){
                ConfigImportModel configImport = configImportMap.get(typeImportEnum);
                ImportStatDto importStatDto = new ImportStatDto(typeImportEnum, configImport.getOperation());
                importStats.put(typeImportEnum, importStatDto);
            }
            ImportStatDto importStat = importStats.get(typeImportEnum);
            importStat.incrementTotalImport();
            if (importModel.getUserDownload() == null) {
                importStat.incrementNonDownloaded();
            } else {
                importStat.incrementDownloaded();
            }
        }
        return new ArrayList<>(importStats.values());
    }

}





