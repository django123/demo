package com.example.demo.api;

import com.example.demo.entities.*;
import com.example.demo.services.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class TestApi {

    private final IImportService importService;

    private final IImportArchOpService iImportArchOpService;

    private final IImportArchService importArchService;

    private final ConfigOutilService configOutilService;

    @Autowired
    private ConfigImportService configImportService;

    public TestApi(IImportService importService, IImportArchOpService iImportArchOpService, IImportArchService importArchService, ConfigOutilService configOutilService) {
        this.importService = importService;
        this.iImportArchOpService = iImportArchOpService;
        this.importArchService = importArchService;
        this.configOutilService = configOutilService;
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

    @GetMapping(value="exportCsv", produces = "text/csvs")
    public @ResponseBody ResponseEntity<byte[]> downloadArchiveCsv(@RequestParam(required = false) final String outil, @RequestParam(required = false) String typeImport,
                                                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                   @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) throws IOException, IllegalAccessException {
        if(typeImport == null || typeImport.isEmpty()){
            if(outil == null || outil.isEmpty()){
                List<ConfigOutilModel> configOutilModels = configOutilService.findAll();
                return createFileFromOutil(configOutilModels);
            }else{
                ConfigOutilModel configOutil = configOutilService.findByOutil(OutilEnum.valueOf(outil));
                List<ConfigOutilModel> configsOutil = new ArrayList<>();
                configsOutil.add(configOutil);
                return  createFileFromOutil(configsOutil);
            }
        }else {
            ConfigImportModel config = configImportService.findByTypeImport(TypeImportEnum.valueOf(typeImport));


            StringBuilder csvContent = createContentArchive(config,startDate, endDate);
            HttpHeaders headers = new HttpHeaders();
            headers.add("CONTENT_DISPOSITION", "attachment; filename=Export_" +config.getTypeImportEnum() + ".csv");
            headers.add("Content-Type", MediaType.ALL_VALUE);
            return new ResponseEntity<>(csvContent.toString().getBytes(), headers, HttpStatus.OK);
        }
    }

    private ResponseEntity<byte[]> createFileFromOutil(List<ConfigOutilModel> configOutilModels) throws IOException, IllegalAccessException{
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (ConfigOutilModel configOutil : configOutilModels){
            for(ConfigImportModel configImport : configOutil.getImports()){
                StringBuilder fileContent = createContentArchive(configImport, null, null);
                ZipEntry zipEntry = new ZipEntry(configOutil.getOutil() + "_" + configImport.getTypeImportEnum() + ".csv");
                zipOut.putNextEntry(zipEntry);
                zipOut.write(fileContent.toString().getBytes());
            }
        }
        zipOut.close();
        fos.close();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=Export.zip");
        responseHeaders.add("Content-Type", MediaType.ALL_VALUE);
        return new ResponseEntity<>(fos.toByteArray(), responseHeaders, HttpStatus.OK);
    }
    private StringBuilder createContentArchive(ConfigImportModel config, LocalDate startDate, LocalDate endDate) throws IOException, IllegalAccessException {
        List<ImportArchModel> importArchModels;
        if (startDate != null && endDate != null) {
            importArchModels = importArchService.findByTypeImportAndDateBetween(config.getTypeImportEnum(), startDate, endDate);
        } else {
            importArchModels = importArchService.findByTypeImport(config.getTypeImportEnum());
        }

        Map<String, Field> mapFields = getFields();
        Map<String, String> mapImportElements = config.getElements().stream()
                .collect(Collectors.toMap(ConfigImportElementModel::getName, ConfigImportElementModel::getLabel));

        StringBuilder result = new StringBuilder();
        CSVPrinter printer = CSVFormat.DEFAULT.print(result);
        printer.printRecord(mapImportElements);

        List<Object> line = new ArrayList<>();
        for(ImportArchModel importArchModel : importArchModels){
            for (Map.Entry<String, String> entry : mapImportElements.entrySet()){
                Field field = mapFields.get(entry.getKey());
                if (field == null){
                    line.add("");
                }else {
                    field.setAccessible(true);
                    line.add(field.get(importArchModel));
                }
            }
            printer.printRecord(line);
            line.clear();
        }
        return result;
     }


    private Map<String, Field> getFields(){
        List<Field> allFields = new ArrayList<>();
        allFields.addAll(Arrays.asList(ImportArchModel.class.getDeclaredFields()));
        return  allFields.stream()
                .collect(Collectors.toMap(Field::getName, Function.identity()));
    }
}
