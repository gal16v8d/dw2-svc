package com.gsdd.dw2.service.it;

import com.gsdd.dw2.model.DigimonModel;
import com.gsdd.dw2.service.DigimonService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class DigimonServiceIT {

    private static final long AGUMON_ID = 200L;
    private static final long METALGREYMON_ID = 400L;
    private static final long ID_NOT_FOUND = 700L;
    private static final String OFANIMON = "Ofanimon";
    private static final String AGUMON = "Agumon";
    @Autowired private DigimonService service;

    @Test
    void getAllTest() {
        List<DigimonModel> result = service.getAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.size());
    }

    @Test
    void getByIdTest() {
        DigimonModel result = service.getById(AGUMON_ID);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(AGUMON, result.getName());
    }

    @Test
    void getByIdNotFoundTest() {
        Assertions.assertNull(service.getById(ID_NOT_FOUND));
    }

    @Test
    void saveTest() {
        DigimonModel result =
                service.save(
                        DigimonModel.builder()
                                .name(OFANIMON)
                                .digimonTypeId(300L)
                                .elementId(200L)
                                .levelId(500L)
                                .build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(OFANIMON, result.getName());
    }

    @Test
    void saveNameExistsTest() {
        Assertions.assertThrows(
                DataIntegrityViolationException.class,
                () ->
                        service.save(
                                DigimonModel.builder()
                                        .name(AGUMON)
                                        .digimonTypeId(300L)
                                        .elementId(200L)
                                        .levelId(500L)
                                        .build()));
    }

    @Test
    void updateTest() {
        DigimonModel result =
                service.update(
                        METALGREYMON_ID,
                        DigimonModel.builder()
                                .name(OFANIMON)
                                .digimonTypeId(300L)
                                .elementId(200L)
                                .levelId(500L)
                                .build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(OFANIMON, result.getName());
    }

    @Test
    void updateNoMatchTest() {
        Assertions.assertNull(
                service.update(
                        ID_NOT_FOUND,
                        DigimonModel.builder()
                                .name(OFANIMON)
                                .digimonTypeId(300L)
                                .elementId(200L)
                                .levelId(500L)
                                .build()));
    }

    @Test
    void patchTest() {
        DigimonModel result =
                service.patch(
                        METALGREYMON_ID,
                        DigimonModel.builder()
                                .name(OFANIMON)
                                .digimonTypeId(300L)
                                .elementId(200L)
                                .levelId(500L)
                                .build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(OFANIMON, result.getName());
    }

    @Test
    void patchNoMatchTest() {
        Assertions.assertNull(
                service.patch(
                        ID_NOT_FOUND,
                        DigimonModel.builder()
                                .name(OFANIMON)
                                .digimonTypeId(300L)
                                .elementId(200L)
                                .levelId(500L)
                                .build()));
    }

    @Test
    void deleteTest() {
        Assertions.assertEquals(METALGREYMON_ID, service.delete(METALGREYMON_ID));
    }

    @Test
    void deleteNoMatchTest() {
        Assertions.assertNull(service.delete(ID_NOT_FOUND));
    }
}
