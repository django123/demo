package com.example.demo.services;

import com.example.demo.entities.ImportModel;
import com.example.demo.entities.ImportStatDto;
import com.example.demo.entities.TypeImportEnum;
import com.example.demo.repositories.IImportRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IImportStatServiceImpl implements IImportStatService{

    private final IImportRepository importRepository;

    public IImportStatServiceImpl(IImportRepository importRepository) {
        this.importRepository = importRepository;
    }

    @Override
    public List<ImportStatDto> getImportStats(String typeImport) {
        List<ImportModel> importModels;
        if (typeImport != null) {
            importModels = importRepository.findByTypeImportAndUserDownloadNotNull(TypeImportEnum.valueOf(typeImport));
        } else {
            importModels = importRepository.findByUserDownloadNotNull();
        }
        Map<TypeImportEnum, ImportStatDto> importStats = new HashMap<>();

        for (ImportModel importModel : importModels){
            TypeImportEnum typeImportEnum = importModel.getTypeImport();
            if (!importStats.containsKey(typeImportEnum)){
                importStats.put(typeImportEnum, new ImportStatDto(typeImportEnum));
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





