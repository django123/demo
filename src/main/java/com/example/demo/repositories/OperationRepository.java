package com.example.demo.repositories;

import com.example.demo.entities.FichierModel;
import com.example.demo.entities.OperationModel;
import com.example.demo.entities.OperationStatusEnum;
import com.example.demo.entities.TypeImportEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperationRepository extends JpaRepository<OperationModel, Long> {

    @Query(value = "UPDATE operation SET statut='DELETED' WHERE carte_id = :carteId ",nativeQuery = true)
    void updateOperationStatusCarteId(Long carteId);
    @Query(value = "UPDATE operation SET statut='DELETED' WHERE fichier_id = :fichierId ",nativeQuery = true)
    void updateOperationStatusFichierId(Long fichierId);
 List<OperationModel>findByStatut(OperationStatusEnum statut);
 List<OperationModel>findByStatutAndTypeImport(OperationStatusEnum statut, TypeImportEnum typeImport);
 List<OperationModel>findByStatutAndTypeImportAndAndFichier_Id(OperationStatusEnum statut, TypeImportEnum typeImport,Long id);

 //List<OperationModel> findByStatutAndTypeImportAndFichierId(List<OperationStatusEnum> statuts, TypeImportEnum typeImport, Long fichierId);
 List<OperationModel> findByTypeImportAndFichierId(TypeImportEnum typeImport, Long fichierId);

 List<OperationModel> findByCarte_Id(Long carteId);

    List<OperationModel> findByFichier_IdAndStatutNot(Long fichierId, OperationStatusEnum treated);

    List<OperationModel> findByTypeImport(TypeImportEnum typeImportEnum);


}
