package com.example.demo.services;

import com.example.demo.entities.TypeImportEnum;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IImportArchService {
    void archive();

/*
    void exportCsvByType(TypeImportEnum typeImport, HttpServletResponse response) throws IOException;
*/
}
