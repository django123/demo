package com.example.demo.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImportStatDto {
    private TypeImportEnum typeImport;
    private GlobalDto globalDto;
    private int downloadedCount;
    private int nonDownloadedCount;

    public ImportStatDto(TypeImportEnum typeImport) {
        this.typeImport = typeImport;
        this.globalDto = new GlobalDto(typeImport.toString());
        this.downloadedCount = 0;
        this.nonDownloadedCount = 0;
    }

    public void incrementDownloaded() {
        this.downloadedCount++;
    }

    public void incrementNonDownloaded() {
        this.nonDownloadedCount++;
    }
}
