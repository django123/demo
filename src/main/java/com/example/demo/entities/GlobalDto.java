package com.example.demo.entities;

import lombok.Data;

@Data
public class GlobalDto {
    private String libelle;
    private TypeImportEnum typeImport;

    public GlobalDto(String libelle, TypeImportEnum typeImport) {
        this.libelle = libelle;
        this.typeImport=typeImport;
    }
}
