package com.example.demo.repositories;

import com.example.demo.entities.ConfigOutilModel;
import com.example.demo.entities.OutilEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IConfigOutilRepository extends JpaRepository<ConfigOutilModel, Long>, JpaSpecificationExecutor<ConfigOutilModel> {
    ConfigOutilModel findByOutil(OutilEnum outil);
}
