package com.example.demo.services;

import com.example.demo.entities.FichierModel;
import com.example.demo.entities.FichierStatistiquesModel;

import java.util.List;

public interface IFichierService {

    void save(FichierModel fichierModel);
    List<FichierModel>findAll();
    FichierModel findById(Long id);
    void deleteById(Long id);

    FichierStatistiquesModel getFichierStatistics(Long fichierId);
}
