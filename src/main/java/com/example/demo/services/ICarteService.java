package com.example.demo.services;

import com.example.demo.entities.CarteModel;

import java.util.List;

public interface ICarteService {

    List<CarteModel>findAll();
    void save(CarteModel carteModel);
    void delete(Long id);
    CarteModel findById(Long id);
}
