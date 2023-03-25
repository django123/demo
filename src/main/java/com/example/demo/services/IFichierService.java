package com.example.demo.services;

import com.example.demo.entities.FichierModel;

import java.util.List;

public interface IFichierService {

    void save(FichierModel fichierModel);
    List<FichierModel>findAll();
    FichierModel findById(Long id);
    void deleteById(Long id);
}
