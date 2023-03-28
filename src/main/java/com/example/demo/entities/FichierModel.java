package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class FichierModel implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String libelle;

    @Enumerated(EnumType.STRING)
    private FileTypeEnum type;
    @Enumerated(EnumType.STRING)
    private FileStatusEnum statut;

    private LocalDate dateEtat;

    private long nbrOperations;

    @OneToMany(mappedBy = "fichier")
    @JsonIgnore
    private List<OperationModel> operations;

}
