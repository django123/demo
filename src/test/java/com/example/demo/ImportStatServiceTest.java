package com.example.demo;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.example.demo.entities.ImportModel;
import com.example.demo.entities.ImportStatDto;
import com.example.demo.entities.TypeImportEnum;
import com.example.demo.repositories.IImportRepository;
import com.example.demo.services.IImportStatService;
import com.example.demo.services.IImportStatServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class IImportStatServiceImplTest {

    @MockBean
    private IImportRepository importRepository;
    @Autowired
    private IImportStatService importStatService;

    @Before("")
    public void setUp() {
        importRepository = mock(IImportRepository.class);
        importStatService = new IImportStatServiceImpl(importRepository);
    }

    @Test
    public void testGetImportStats() {
        ImportModel importModel1 = new ImportModel();
        importModel1.setTypeImport(TypeImportEnum.IMPORT_628_10211);
        importModel1.setUserDownload(null);
        importModel1.setDateDownload(null);

        ImportModel importModel2 = new ImportModel();
        importModel2.setTypeImport(TypeImportEnum.IMPORT_629_10213);
        importModel2.setUserDownload("user1");
        importModel2.setDateDownload(LocalDateTime.now());

        ImportModel importModel3 = new ImportModel();
        importModel3.setTypeImport(TypeImportEnum.IMPORT_630_10207);
        importModel3.setUserDownload("user2");
        importModel3.setDateDownload(LocalDateTime.now());

        List<ImportModel> importModels = Arrays.asList(importModel1, importModel2, importModel3);

        when(importRepository.findByUserDownloadIsNotNullAndDateDownloadIsNotNull()).thenReturn(importModels);
        when(importRepository.findByTypeImportAndUserDownloadIsNotNullAndDateDownloadIsNotNull(TypeImportEnum.IMPORT_629_10213)).thenReturn(Arrays.asList(importModel2));

        List<ImportStatDto> importStats = importStatService.getImportStats(null);

        assertEquals(3, importStats.size());

        ImportStatDto csvStat = importStats.get(0);
        assertEquals(TypeImportEnum.IMPORT_628_10211, csvStat.getTypeImport());
        assertEquals(0, csvStat.getDownloadedCount());
        assertEquals(1, csvStat.getNonDownloadedCount());

        ImportStatDto xmlStat = importStats.get(1);
        assertEquals(TypeImportEnum.IMPORT_629_10213, xmlStat.getTypeImport());
        assertEquals(1, xmlStat.getDownloadedCount());
        assertEquals(0, xmlStat.getNonDownloadedCount());
    }

}

