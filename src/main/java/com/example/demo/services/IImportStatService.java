package com.example.demo.services;

import com.example.demo.entities.ImportStatDto;

import java.util.List;

public interface IImportStatService {
    List<ImportStatDto> getImportStats(String typeImport);
}
