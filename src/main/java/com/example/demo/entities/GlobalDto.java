package com.example.demo.entities;

import lombok.Data;

@Data
public class GlobalDto {

    private String libelle;

    public GlobalDto(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }



}
