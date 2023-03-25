package com.example.demo.api;

import com.example.demo.entities.CarteModel;
import com.example.demo.services.ICarteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartes")
public class CarteController {

    private final ICarteService carteService;

    public CarteController(ICarteService carteService) {
        this.carteService = carteService;
    }




    @PostMapping("/save")
    public void saveCarte(@RequestBody CarteModel carteModel){
        carteService.save(carteModel);
    }

    @GetMapping("/all")
    public List<CarteModel> getAll(){
        return carteService.findAll();
    }


    @GetMapping("/{id}")
    public CarteModel getCarte(@PathVariable Long id){
        return carteService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        carteService.delete(id);
    }
}
