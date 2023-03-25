package com.example.demo.repositories;

import com.example.demo.entities.FichierModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichierRepository extends JpaRepository<FichierModel, Long> {
}
