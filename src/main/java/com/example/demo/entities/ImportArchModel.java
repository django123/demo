package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class ImportArchModel implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String fileId;

    private String ddc;
    private String evenmt;
    private String dossier1;
    private String codeMvt1;
    private Double montant;
    @Enumerated(EnumType.STRING)
    private TypeImportEnum typeImport;

}
