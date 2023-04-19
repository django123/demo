package com.example.demo.services;


import com.example.demo.entities.ConfigOutilModel;
import com.example.demo.entities.OutilEnum;
import com.example.demo.repositories.IConfigOutilRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ConfigOutilService {

    private final IConfigOutilRepository configOutilRepository;

    public ConfigOutilService(IConfigOutilRepository configOutilRepository) {
        this.configOutilRepository = configOutilRepository;
    }

    public List<ConfigOutilModel> findAll(){
        return configOutilRepository.findAll();
    }

    public ConfigOutilModel findByOutil(OutilEnum outil) {
        return configOutilRepository.findByOutil(outil);
    }
}
