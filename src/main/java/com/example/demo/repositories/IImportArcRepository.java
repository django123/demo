package com.example.demo.repositories;

import com.example.demo.entities.ImportArchModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImportArcRepository extends JpaRepository<ImportArchModel, Long> {
}
