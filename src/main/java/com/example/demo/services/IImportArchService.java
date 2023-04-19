package com.example.demo.services;

import com.example.demo.entities.ImportArchModel;
import com.example.demo.entities.TypeImportEnum;

import java.time.LocalDate;
import java.util.List;

public interface IImportArchService {
    void archive();

    List<ImportArchModel> findByTypeImport(TypeImportEnum typeImportEnum);

    List<ImportArchModel> findByTypeImportAndDate(TypeImportEnum typeImportEnum, LocalDate startDate, LocalDate endDate);

    List<ImportArchModel> findByTypeImportAndDateBetween(TypeImportEnum typeImportEnum, LocalDate startDate, LocalDate endDate);

/*
    void exportCsvByType(TypeImportEnum typeImport, HttpServletResponse response) throws IOException;
*/
}
