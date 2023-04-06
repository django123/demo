package com.example.demo.repositories;

import com.example.demo.entities.ConfigImportModel;
import com.example.demo.entities.TypeImportEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConfigImportRepository extends JpaRepository<ConfigImportModel, Long> {
  ConfigImportModel findByTypeImportEnum(TypeImportEnum typeImportEnum);
}
