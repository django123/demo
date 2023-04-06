package com.example.demo.services;

import com.example.demo.entities.FichierModel;

import java.util.List;
import java.util.Map;

public interface IFichierService {

    void save(FichierModel fichierModel);
    List<FichierModel>findAll();
    FichierModel findById(Long id);
    void deleteById(Long id);

    //FichierStatistiquesModel getFichierStatistics(Long fichierId);
    Map<String, Object> dorisFichierStatistics(Long fichierId);
}
