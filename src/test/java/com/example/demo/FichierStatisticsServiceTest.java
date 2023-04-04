package com.example.demo;

import com.example.demo.entities.FichierModel;
import com.example.demo.entities.OperationModel;
import com.example.demo.entities.OperationStatusEnum;
import com.example.demo.entities.TypeImportEnum;
import com.example.demo.repositories.FichierRepository;
import com.example.demo.repositories.OperationRepository;
import com.example.demo.services.IFichierService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FichierStatisticsServiceTest {

    @Mock
    private FichierRepository fichierRepository;

    @Mock
    private OperationRepository operationRepository;

    @Autowired
    @MockBean
    private IFichierService fichierStatisticsService;

    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
     void testGetFichierStatistics() {
        // Given
        Long fichierId = 1L;
        FichierModel fichier = new FichierModel();
        fichier.setId(fichierId);

        OperationModel operation1 = new OperationModel();
        operation1.setId(1L);
        operation1.setTypeImport(TypeImportEnum.IMPORT_630_10207);
        operation1.setStatut(OperationStatusEnum.INIT);

        OperationModel operation2 = new OperationModel();
        operation2.setId(2L);
        operation2.setTypeImport(TypeImportEnum.IMPORT_628_10211);
        operation2.setStatut(OperationStatusEnum.TREATED);

        OperationModel operation3 = new OperationModel();
        operation3.setId(3L);
        operation3.setTypeImport(TypeImportEnum.IMPORT_630_10207);
        operation3.setStatut(OperationStatusEnum.IN_PROGRESS);

        List<OperationModel> operations = Arrays.asList(operation1, operation2, operation3);

        when(fichierRepository.findById(fichierId)).thenReturn(Optional.of(fichier));
        when(operationRepository.findByFichierAndStatutNot(fichier, OperationStatusEnum.TREATED)).thenReturn(operations);

        // When
        Map<String, Object> result = fichierStatisticsService.dorisFichierStatistics(fichierId);

        // Then
        verify(fichierRepository, times(1)).findById(fichierId);
        verify(operationRepository, times(1)).findByFichierAndStatutNot(fichier, OperationStatusEnum.TREATED);
        assertEquals(3L, result.get("total"));
        assertEquals(2L, result.get("notTreated"));

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> imports = (List<Map<String, Object>>) result.get("imports");
        assertEquals(2, imports.size());

        Map<String, Object> import1 = imports.get(0);
        assertEquals(TypeImportEnum.IMPORT_630_10207, import1.get("libelle"));
        assertEquals(TypeImportEnum.IMPORT_630_10207, import1.get("typeImport"));
        assertEquals(2L, import1.get("totalImport"));

        Map<String, Object> import2 = imports.get(1);
        assertEquals(TypeImportEnum.IMPORT_628_10211, import2.get("libelle"));
        assertEquals(TypeImportEnum.IMPORT_628_10211, import2.get("typeImport"));
        assertEquals(1L, import2.get("totalImport"));
    }

}

