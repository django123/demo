package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class CarteModel implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String numCarte;

    @OneToMany(mappedBy = "carte")
    @JsonIgnore
    private List<OperationModel> operations;
}
