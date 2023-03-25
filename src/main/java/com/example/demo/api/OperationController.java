package com.example.demo.api;

import com.example.demo.entities.CarteModel;
import com.example.demo.entities.OperationModel;
import com.example.demo.services.IOperationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operation")
public class OperationController {

    private final IOperationService operationService;

    public OperationController(IOperationService operationService) {
        this.operationService = operationService;
    }


    @PostMapping("/save")
    public void saveOperation(@RequestBody OperationModel carteModel){
        operationService.save(carteModel);
    }

    @GetMapping("/all")
    public List<OperationModel> getAll(){
        return operationService.findAll();
    }


    @GetMapping("/{id}")
    public OperationModel getOperation(@PathVariable Long id){
        return operationService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        operationService.delete(id);
    }
}
