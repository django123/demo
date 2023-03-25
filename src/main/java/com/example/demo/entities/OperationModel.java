package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationModel {

    @Id
    @GeneratedValue
    private Long id;
    private String LibBac;

    @Enumerated(EnumType.STRING)
    private TypeImportEnum typeImport;
    @Enumerated(EnumType.STRING)
    private OperationStatusEnum statut;

    @ManyToOne
    private FichierModel fichier;
    @ManyToOne
    private CarteModel carte;
}
