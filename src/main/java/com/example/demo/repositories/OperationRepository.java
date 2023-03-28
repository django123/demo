package com.example.demo.repositories;

import com.example.demo.entities.OperationModel;
import com.example.demo.entities.OperationStatusEnum;
import com.example.demo.entities.TypeImportEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<OperationModel, Long> {
 List<OperationModel>findByStatut(OperationStatusEnum statut);
 List<OperationModel>findByStatutAndTypeImport(OperationStatusEnum statut, TypeImportEnum typeImport);
 List<OperationModel>findByStatutAndTypeImportAndAndFichier_Id(OperationStatusEnum statut, TypeImportEnum typeImport,Long id);

 List<OperationModel> findByStatutAndTypeImportAndFichierId(List<OperationStatusEnum> statuts, TypeImportEnum typeImport, Long fichierId);
 List<OperationModel> findByTypeImportAndFichierId(TypeImportEnum typeImport, Long fichierId);
}
