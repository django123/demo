package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class ImportModel implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String ddc;
    private String evenmt;
    private String dossier1;
    private String codeMvt1;
    private Double montant;
    private String userDownload;
    private LocalDateTime dateDownload;

    @Enumerated(EnumType.STRING)
    private TypeImportEnum typeImport;

    @OneToOne(cascade = CascadeType.REMOVE)
    private OperationModel operation;
}
