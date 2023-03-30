package com.example.demo.entities;

import java.util.ArrayList;
import java.util.List;

public class FichierStatistiquesModel {

    private long total;
    private long notTreated;

    List<ImportStatDto>importStatDtos;

    public FichierStatistiquesModel() {
        this.importStatDtos = new ArrayList<>();
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getNotTreated() {
        return notTreated;
    }

    public void setNotTreated(long notTreated) {
        this.notTreated = notTreated;
    }

    public List<ImportStatDto> getImportStatDtos() {
        return importStatDtos;
    }

    public void setImportStatDtos(List<ImportStatDto> importStatDtos) {
        this.importStatDtos = importStatDtos;
    }
}
