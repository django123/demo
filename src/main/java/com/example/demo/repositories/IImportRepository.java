package com.example.demo.repositories;

import com.example.demo.entities.ImportModel;
import com.example.demo.entities.TypeImportEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IImportRepository extends JpaRepository<ImportModel, Long> {
    List<ImportModel> findAllByTypeImport(TypeImportEnum typeImport);
    ImportModel findByOperation_Id(Long id);
    List<ImportModel> findByTypeImport(TypeImportEnum typeImport);
    List<ImportModel> findByUserDownloadIsNotNullAndDateDownloadIsNotNull();
    List<ImportModel>  findByTypeImportAndUserDownloadNotNull(TypeImportEnum typeImport);
    List<ImportModel>  findByUserDownloadNotNull();
    List<ImportModel> findByTypeImportAndUserDownloadIsNotNullAndDateDownloadIsNotNull(TypeImportEnum typeImportEnum);

}
