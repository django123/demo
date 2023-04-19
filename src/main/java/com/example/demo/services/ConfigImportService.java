package com.example.demo.services;

import com.example.demo.entities.ConfigImportModel;
import com.example.demo.entities.TypeImportEnum;
import com.example.demo.repositories.IConfigImportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConfigImportService {

    private final IConfigImportRepository configImportRepository;

    public ConfigImportService(IConfigImportRepository configImportRepository) {
        this.configImportRepository = configImportRepository;
    }

    public ConfigImportModel findByTypeImport(TypeImportEnum typeImport) {
        return configImportRepository.findByTypeImportEnum(typeImport);
    }
}
