package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ConfigOutilModel {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private OutilEnum outil;
    private String label;
    @OneToMany(mappedBy = "config")
    private List<ConfigImportModel> imports;
}
