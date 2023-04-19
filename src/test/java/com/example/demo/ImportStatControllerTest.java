package com.example.demo;

import com.example.demo.api.ImportStatController;
import com.example.demo.entities.ImportStatDto;
import com.example.demo.repositories.ImportRepositoryImp;
import com.example.demo.repositories.OperationRepository;
import com.example.demo.services.IImportStatService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(ImportStatController.class)
class ImportStatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IImportStatService importStatService;
    @InjectMocks
    private ImportRepositoryImp importRepositoryImp;

    @Mock
    private OperationRepository operationRepository;

    @Test
    public void testUpdateOperationStatutCarteId() {
        // Données de test
        Long carteId = 123L;

        // Appel de la méthode à tester
        importRepositoryImp.updateOperationStatutCarteId(carteId);

        // Vérification que la méthode d'updateOperationStatusCarteId est appelée avec le bon argument
        Mockito.verify(operationRepository, Mockito.times(1)).updateOperationStatusCarteId(carteId);
    }

    @Test
    public void testGetImportStats() throws Exception {
        // given
        List<ImportStatDto> importStatDtos = Arrays.asList(new ImportStatDto(), new ImportStatDto());
        when(importStatService.getImportStats(null)).thenReturn(importStatDtos);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/import-stats"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].class").value(ImportStatDto.class.getSimpleName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].class").value(ImportStatDto.class.getSimpleName()))
                .andReturn();
    }
}

