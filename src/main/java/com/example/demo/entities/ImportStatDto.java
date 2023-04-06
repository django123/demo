package com.example.demo.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImportStatDto {

    private TypeImportEnum typeImport;
    private String libelle;
    private long totalImport;
    private long totalDownloaded;
    private long totalNonDownloaded;

    public ImportStatDto(TypeImportEnum typeImport, String libelle) {
        this.typeImport = typeImport;
        this.libelle = libelle;
    }

    public TypeImportEnum getTypeImport() {
        return typeImport;
    }

    public void setTypeImport(TypeImportEnum typeImport) {
        this.typeImport = typeImport;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public long getTotalImport() {
        return totalImport;
    }

    public void setTotalImport(long totalImport) {
        this.totalImport = totalImport;
    }

    public long getTotalDownloaded() {
        return totalDownloaded;
    }

    public void setTotalDownloaded(long totalDownloaded) {
        this.totalDownloaded = totalDownloaded;
    }

    public long getTotalNonDownloaded() {
        return totalNonDownloaded;
    }

    public void setTotalNonDownloaded(long totalNonDownloaded) {
        this.totalNonDownloaded = totalNonDownloaded;
    }

    public void incrementTotalImport() {
        this.totalImport++;
    }

    public void incrementDownloaded() {
        this.totalDownloaded++;
    }

    public void incrementNonDownloaded() {
        this.totalNonDownloaded++;
    }



}
