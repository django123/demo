package com.example.demo.repositories;

import com.example.demo.entities.CarteModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteRepository extends JpaRepository<CarteModel, Long> {
}
