package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ConfigImportElementModel {
    @Id
    @GeneratedValue
    private Long id;
    private String label;
    private String name;
    private String className;
    @ManyToOne
    private ConfigImportModel config;
}
