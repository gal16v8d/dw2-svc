package com.gsdd.dw2.service.it;

import com.gsdd.dw2.model.AttackTargetTypeModel;
import com.gsdd.dw2.service.AttackTargetTypeService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

@DataJpaTest
class AttackTargetTypeServiceIT {

    private static final long ALL_ID = 200L;
    private static final long SELF_ID = 300L;
    private static final long ID_NOT_FOUND = 900L;
    private static final String RANDOM_ALLIES = "Random Allies";
    private static final String ALL = "All";
    @Autowired private AttackTargetTypeService service;

    @Test
    void getAllTest() {
        List<AttackTargetTypeModel> result = service.getAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(7, result.size());
    }

    @Test
    void getByIdTest() {
        AttackTargetTypeModel result = service.getById(ALL_ID);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(ALL, result.getName());
    }

    @Test
    void getByIdNotFoundTest() {
        Assertions.assertNull(service.getById(ID_NOT_FOUND));
    }

    @Test
    void saveTest() {
        AttackTargetTypeModel result =
                service.save(AttackTargetTypeModel.builder().name(RANDOM_ALLIES).build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(RANDOM_ALLIES, result.getName());
    }

    @Test
    void saveNameExistsTest() {
        Assertions.assertThrows(
                DataIntegrityViolationException.class,
                () -> service.save(AttackTargetTypeModel.builder().name(ALL).build()));
    }

    @Test
    void updateTest() {
        AttackTargetTypeModel result =
                service.update(
                        SELF_ID, AttackTargetTypeModel.builder().name(RANDOM_ALLIES).build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(RANDOM_ALLIES, result.getName());
    }

    @Test
    void updateNoMatchTest() {
        Assertions.assertNull(
                service.update(
                        ID_NOT_FOUND, AttackTargetTypeModel.builder().name(RANDOM_ALLIES).build()));
    }

    @Test
    void patchTest() {
        AttackTargetTypeModel result =
                service.patch(SELF_ID, AttackTargetTypeModel.builder().name(RANDOM_ALLIES).build());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(RANDOM_ALLIES, result.getName());
    }

    @Test
    void patchNoMatchTest() {
        Assertions.assertNull(
                service.patch(
                        ID_NOT_FOUND, AttackTargetTypeModel.builder().name(RANDOM_ALLIES).build()));
    }

    @Test
    void deleteTest() {
        Assertions.assertEquals(SELF_ID, service.delete(SELF_ID));
    }

    @Test
    void deleteNoMatchTest() {
        Assertions.assertNull(service.delete(ID_NOT_FOUND));
    }
}
