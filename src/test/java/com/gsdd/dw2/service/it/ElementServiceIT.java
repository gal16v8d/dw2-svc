package com.gsdd.dw2.service.it;

import com.gsdd.dw2.model.ElementModel;
import com.gsdd.dw2.service.ElementService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class ElementServiceIT {

    private static final long NONE_ID = 200L;
    private static final long DARKNESS_ID = 500L;
    private static final long ID_NOT_FOUND = 800L;
    private static final String AIR = "Air";
    private static final String NONE = "None";
    @Autowired private ElementService service;

    @Test
    void getAllTest() {
        List<ElementModel> result = service.getAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(6, result.size());
    }

    @Test
    void getByIdTest() {
        ElementModel result = service.getById(NONE_ID);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(NONE, result.getName());
    }

    @Test
    void getByIdNotFoundTest() {
        Assertions.assertNull(service.getById(ID_NOT_FOUND));
    }

    @Test
    void saveTest() {
        ElementModel result = service.save(ElementModel.builder().name(AIR).build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(AIR, result.getName());
    }

    @Test
    void saveNameExistsTest() {
        Assertions.assertThrows(
                DataIntegrityViolationException.class,
                () -> service.save(ElementModel.builder().name(NONE).build()));
    }

    @Test
    void updateTest() {
        ElementModel result = service.update(DARKNESS_ID, ElementModel.builder().name(AIR).build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(AIR, result.getName());
    }

    @Test
    void updateNoMatchTest() {
        Assertions.assertNull(
                service.update(ID_NOT_FOUND, ElementModel.builder().name(AIR).build()));
    }

    @Test
    void patchTest() {
        ElementModel result = service.patch(DARKNESS_ID, ElementModel.builder().name(AIR).build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(AIR, result.getName());
    }

    @Test
    void patchNoMatchTest() {
        Assertions.assertNull(
                service.patch(ID_NOT_FOUND, ElementModel.builder().name(AIR).build()));
    }

    @Test
    void deleteTest() {
        Assertions.assertEquals(DARKNESS_ID, service.delete(DARKNESS_ID));
    }

    @Test
    void deleteNoMatchTest() {
        Assertions.assertNull(service.delete(ID_NOT_FOUND));
    }
}
