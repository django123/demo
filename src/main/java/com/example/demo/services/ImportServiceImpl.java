package com.example.demo.services;

import com.example.demo.entities.ImportModel;
import com.example.demo.entities.TypeImportEnum;
import com.example.demo.repositories.IImportRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ImportServiceImpl implements IImportService{

    private final IImportRepository importRepository;

    public ImportServiceImpl(IImportRepository importRepository) {
        this.importRepository = importRepository;
    }

    @Override
    public List<ImportModel> findAllByTypeImport(TypeImportEnum type) {
        return importRepository.findAllByTypeImport(type);
    }

    @Override
    public List<ImportModel> findAll() {
        return importRepository.findAll();
    }

    @Override
    public void save(ImportModel importModel) {
        importRepository.save(importModel);
    }

    @Override
    public ImportModel findById(Long id) {
        return importRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        importRepository.deleteById(id);
    }
}
