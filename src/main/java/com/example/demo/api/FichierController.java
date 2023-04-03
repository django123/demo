package com.example.demo.api;

import com.example.demo.entities.FichierModel;
import com.example.demo.services.IFichierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/fichier")
public class FichierController {

    private final IFichierService fichierService;

    public FichierController(IFichierService fichierService) {
        this.fichierService = fichierService;
    }


    @PostMapping("/save")
    public void saveFichier(@RequestBody FichierModel fichierModel){
         fichierService.save(fichierModel);
    }

    @GetMapping("/all")
    public List<FichierModel>getAll(){
        return fichierService.findAll();
    }


    @GetMapping("/{id}")
    public FichierModel getFichier(@PathVariable Long id){
        return fichierService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        fichierService.deleteById(id);
    }

    @GetMapping("/{fichierId}/statistics")
    public ResponseEntity<Map<String, Object>> getFichierStatistics(@PathVariable Long fichierId) {
        Map<String, Object> response = fichierService.dorisFichierStatistics(fichierId);
        return ResponseEntity.ok(response);
    }
}
