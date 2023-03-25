package com.example.demo.api;

import com.example.demo.entities.ImportModel;
import com.example.demo.entities.TypeImportEnum;
import com.example.demo.services.IImportArchOpService;
import com.example.demo.services.IImportArchService;
import com.example.demo.services.IImportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/imports")
public class ImportRestController {

    private final IImportService importService;

    private final IImportArchOpService iImportArchOpService;

    private final IImportArchService importArchService;

    public ImportRestController(IImportService importService, IImportArchOpService iImportArchOpService, IImportArchService importArchService) {
        this.importService = importService;
        this.iImportArchOpService = iImportArchOpService;
        this.importArchService = importArchService;
    }


    @PostMapping("/save")
    public void save(@RequestBody ImportModel fichierModel){
        importService.save(fichierModel);
    }

    @GetMapping("/all")
    public List<ImportModel> getAll(){
        return importService.findAll();
    }

    @GetMapping("/{id}")
    public ImportModel getImport(@PathVariable Long id){
        return importService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        importService.delete(id);
    }

    @GetMapping(value="archivage")
    public void archive(@RequestParam(required = false) final String typeImport){
        if (typeImport == null || typeImport.isEmpty()){
            importArchService.archive();
        }else {
            iImportArchOpService.archiveImport(TypeImportEnum.valueOf(typeImport));
        }
    }



}
