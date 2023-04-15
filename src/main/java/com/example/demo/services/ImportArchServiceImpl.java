package com.example.demo.services;


        import com.example.demo.entities.*;
        import com.example.demo.repositories.CarteRepository;
        import com.example.demo.repositories.IImportArcRepository;
        import com.example.demo.repositories.IImportRepository;
        import com.example.demo.repositories.OperationRepository;
        import org.apache.commons.csv.CSVFormat;
        import org.apache.commons.csv.CSVPrinter;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.io.IOException;
        import java.lang.reflect.Field;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;
        import java.util.Map;
        import java.util.function.Function;
        import java.util.stream.Collectors;

@Service
@Transactional
public class ImportArchServiceImpl implements IImportArchService{

    @Autowired
    private CarteRepository carteRepo;

    @Autowired
    private OperationRepository operationRepo;

    @Autowired
    private IImportArcRepository importArchRepo;
    @Autowired
    private IImportRepository importRepo;

    @Override
    public void archive() {
        archiveOperationsForAllCartes();
        deleteEmptyCartes();
    }

    public StringBuilder createFileContent(ConfigImportModel config)throws  IOException, IllegalAccessException{
        List<ImportArchModel> importArchModels = importArchRepo.findByTypeImport(config.getTypeImportEnum());
        Map<String, Field> mapFields = getFields();
        Map<String, String>mapImportElements = config.getElements().stream()
                .collect(Collectors.toMap(ConfigImportElementModel::getName, ConfigImportElementModel::getLabel));
        StringBuilder result = new StringBuilder();
        CSVPrinter printer = CSVFormat.DEFAULT.print(result);
        printer.printRecord(mapImportElements.values());
        List<Object> line = new ArrayList<>();

        for (ImportArchModel importArchModel : importArchModels){
            for(Map.Entry<String, String> entry : mapImportElements.entrySet()){
                Field field = mapFields.get(entry.getKey());
                if (field == null){
                    line.add("");

                }else {
                    field.setAccessible(true);
                    line.add(field.get(importArchModel));
                }
            }
        }
        printer.printRecord(line);
        line.clear();
        return result;
    }
    private Map<String, Field> getFields(){
        List<Field> allFields = new ArrayList<>();
        allFields.addAll(Arrays.asList(ImportArchModel.class.getDeclaredFields()));
        return  allFields.stream().collect(Collectors.toMap(Field::getName, Function.identity()));
    }

    private void archiveOperationsForAllCartes() {
        List<CarteModel> carteModels = carteRepo.findAll();
        carteModels.forEach(this::archiveOperationsForCarte);
    }

    private void archiveOperationsForCarte(CarteModel carteModel) {
        List<OperationModel> operationModels = operationRepo.findByStatut(OperationStatusEnum.TREATED);
        operationModels.stream()
                .map(op -> importRepo.findByOperation_Id(op.getId()))
                .filter(imp -> imp != null && imp.getUserDownload() != null && imp.getDateDownload() != null)
                .forEach(importModel -> {
                    ImportArchModel importArchModel = createImportArchModelFromImportModel(importModel);
                    importArchRepo.save(importArchModel);
                    importRepo.deleteById(importModel.getId());
                });
    }
    private void deleteEmptyCartes() {
        carteRepo.findAll().stream()
                .filter(carteModel -> operationRepo.findByCarte_Id(carteModel.getId()).isEmpty())
                .forEach(carte -> carteRepo.deleteById(carte.getId()));
    }
    private ImportArchModel createImportArchModelFromImportModel(ImportModel importModel) {
        ImportArchModel importArchModel = new ImportArchModel();
        importArchModel.setFileId(String.valueOf(importModel.getOperation().getFichier().getId()));
        importArchModel.setCodeMvt1(importModel.getCodeMvt1());
        importArchModel.setEvenmt(importModel.getEvenmt());
        importArchModel.setDdc(importModel.getDdc());
        importArchModel.setDossier1(importModel.getDossier1());
        importArchModel.setMontant(importModel.getMontant());
        importArchModel.setTypeImport(importModel.getTypeImport());
        return importArchModel;
    }
}








