package com.example.demo.services;

import com.example.demo.entities.CarteModel;
import com.example.demo.entities.OperationModel;
import com.example.demo.entities.OperationStatusEnum;
import com.example.demo.entities.OutilEnum;
import com.example.demo.repositories.CarteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

@Service
@Transactional
public class CarteServiceImpl implements ICarteService{

    private final CarteRepository carteRepository;

    public CarteServiceImpl(CarteRepository carteRepository) {
        this.carteRepository = carteRepository;
    }

    @Override
    public List<CarteModel> findAll() {
        return carteRepository.findAll();
    }

    @Override
    public void save(CarteModel carteModel) {
        carteRepository.save(carteModel);
    }

    @Override
    public void delete(Long id) {
        carteRepository.deleteById(id);
    }

    @Override
    public CarteModel findById(Long id) {
        return carteRepository.findById(id).orElse(null);
    }



    
}
