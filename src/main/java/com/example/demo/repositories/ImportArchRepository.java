package com.example.demo.repositories;

import com.example.demo.entities.ImportArchModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ImportArchRepository extends JpaRepository<ImportArchModel, Long> {

    List<ImportArchModel> findByTypeImportAndDateCreateBetween(LocalDate startDate, LocalDate endDate);
}
