package com.example.demo;

import com.example.demo.api.FichierController;
import com.example.demo.services.IFichierService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
class FichierStatisticsControllerTest {

    @Mock
    private IFichierService fichierStatisticsService;

    @InjectMocks
    private FichierController fichierStatisticsController;

    @Test
    void testGetFichierStatistics() {
        Long fichierId = 1L;
        Map<String, Object> response = new HashMap<>();
        response.put("total", 10L);
        response.put("notTreated", 5L);
        List<Map<String, Object>> importsList = new ArrayList<>();
        Map<String, Object> importMap = new HashMap<>();
        importMap.put("libelle", "Import A");
        importMap.put("typeImport", "A");
        importMap.put("totalImport", 3L);
        importsList.add(importMap);
        response.put("imports", importsList);

        Mockito.when(fichierStatisticsService.dorisFichierStatistics(1L)).thenReturn(response);

        ResponseEntity<Map<String, Object>> result = fichierStatisticsController.getFichierStatistics(fichierId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());

        Mockito.verify(fichierStatisticsService, times(1)).getFichierStatistics(fichierId);
        Mockito.verifyNoMoreInteractions(fichierStatisticsService);
    }

}

