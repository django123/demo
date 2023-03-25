package com.example.demo.services;

import com.example.demo.entities.FichierModel;
import com.example.demo.repositories.FichierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FichierServiceImpl implements IFichierService{
    private final FichierRepository fichierRepository;

    public FichierServiceImpl(FichierRepository fichierRepository) {
        this.fichierRepository = fichierRepository;
    }

    @Override
    public void save(FichierModel fichierModel) {
        fichierRepository.save(fichierModel);
    }

    @Override
    public List<FichierModel> findAll() {
        return fichierRepository.findAll();
    }

    @Override
    public FichierModel findById(Long id) {
        return fichierRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
       fichierRepository.deleteById(id);
    }
}
