package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ConfigImportModel {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeImportEnum typeImportEnum;
    private String operation;
    private String prefixe;
    @ManyToOne
    private ConfigOutilModel config;

    @OneToMany
    private List<ConfigImportElementModel> elements;
}
