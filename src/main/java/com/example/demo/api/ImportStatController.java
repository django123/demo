package com.example.demo.api;

import com.example.demo.entities.FichierStatistiquesModel;
import com.example.demo.entities.ImportStatDto;
import com.example.demo.services.IFichierService;
import com.example.demo.services.IImportStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImportStatController {

    @Autowired
    private IImportStatService importStatService;
    @Autowired
    private IFichierService fichierService;

    @GetMapping("/import-stats")
    public List<ImportStatDto> getImportStats(@RequestParam(required = false) String typeImport) {
        return importStatService.getImportStats(typeImport);
    }

}

