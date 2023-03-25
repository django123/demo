package com.example.demo.services;

import com.example.demo.entities.*;
import com.example.demo.repositories.CarteRepository;
import com.example.demo.repositories.IImportArcRepository;
import com.example.demo.repositories.IImportRepository;
import com.example.demo.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ImportArchOperationServiceImpl implements IImportArchOpService{

    @Autowired
    private CarteRepository carteRepo;

    @Autowired
    private OperationRepository operationRepo;

    @Autowired
    private IImportArcRepository importArchRepo;
    @Autowired
    private IImportRepository importRepo;

    @Override
    public void archiveImport(TypeImportEnum typeImportEnum) {
        ImportArchModel importArchModel = new ImportArchModel();
        List<CarteModel> carteModels = carteRepo.findAll();
        carteModels.forEach(carteModel -> {
            List<OperationModel> operationModels = operationRepo.findByStatutAndTypeImport(OperationStatusEnum.TREATED,typeImportEnum);
            operationModels.stream()
                    .map(op -> importRepo.findByOperation_Id(op.getId()))
                    .filter(imp -> (imp != null && imp.getUserDownload() != null && imp.getDateDownload() != null))
                    .forEach(importModel -> {
                        importArchModel.setFileId(String.valueOf(importModel.getOperation().getFichier().getId()));
                        importArchModel.setCodeMvt1(importModel.getCodeMvt1());
                        importArchModel.setEvenmt(importModel.getEvenmt());
                        importArchModel.setDdc(importModel.getDdc());
                        importArchModel.setDossier1(importModel.getDossier1());
                        importArchModel.setMontant(importModel.getMontant());
                        importArchModel.setTypeImport(importModel.getTypeImport());
                        importArchRepo.save(importArchModel);
                        importRepo.deleteById(importModel.getId());
                    });
            if (operationModels.isEmpty())
                carteRepo.deleteById(carteModel.getId());
        });
    }
}

