package com.example.demo.api;

import com.example.demo.entities.ImportModel;
import com.example.demo.entities.TypeImportEnum;
import com.example.demo.services.ExcelGenerator;
import com.example.demo.services.IImportArchOpService;
import com.example.demo.services.IImportArchService;
import com.example.demo.services.IImportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class TestApi {

    private final IImportService importService;

    private final IImportArchOpService iImportArchOpService;

    private final IImportArchService importArchService;

    public TestApi(IImportService importService, IImportArchOpService iImportArchOpService, IImportArchService importArchService) {
        this.importService = importService;
        this.iImportArchOpService = iImportArchOpService;
        this.importArchService = importArchService;
    }


    @GetMapping("/export/excel")
    public void exportIntoExcel(HttpServletResponse response, @RequestParam(required = false)String type) throws IOException {
        response.setContentType("application/octet-stream");

        if (type != null){
            response.setHeader("Content-Disposition", "attachment; filename=" + type + ".xlsx");
            List<ImportModel> importModels = importService.findAllByTypeImport(TypeImportEnum.valueOf(type));
            ExcelGenerator generator = new ExcelGenerator(importModels);
            generator.generate(response);
        }else {
            response.setHeader("Content-Disposition", "attachment; filename=All Import" + ".xlsx");
            List<ImportModel>importModels  = importService.findAll();
            ExcelGenerator generator = new ExcelGenerator(importModels);
            generator.generate(response);
        }
    }
}
