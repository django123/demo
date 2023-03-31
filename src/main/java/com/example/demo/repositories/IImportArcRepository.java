package com.example.demo.repositories;

import com.example.demo.entities.ImportArchModel;
import com.example.demo.entities.TypeImportEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IImportArcRepository extends JpaRepository<ImportArchModel, Long> {
    List<ImportArchModel> findByTypeImport(TypeImportEnum typeImport);

}
