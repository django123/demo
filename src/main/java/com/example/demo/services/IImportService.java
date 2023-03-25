package com.example.demo.services;

import com.example.demo.entities.ImportModel;
import com.example.demo.entities.TypeImportEnum;

import java.util.List;

public interface IImportService {

    List<ImportModel> findAllByTypeImport(TypeImportEnum type);
    List<ImportModel> findAll();

    void save(ImportModel importModel);
    ImportModel findById(Long id);
    void delete(Long id);
}
