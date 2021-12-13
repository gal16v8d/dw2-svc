package com.gsdd.dw2.service.it;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import com.gsdd.dw2.model.EvolutionModel;
import com.gsdd.dw2.service.EvolutionService;

@Disabled // need to take a look on import file
@DataJpaTest
class EvolutionServiceIT {

  private static final long TO_CHAMPTION_ID = 1200L;
  private static final long TO_MEGA_ID = 1400L;
  private static final long ID_NOT_FOUND = 1600L;
  private static final String TO_ULTRA_MEGA = "20+";
  private static final String ZERO_OR_MANY = "0+";
  @Autowired
  private EvolutionService service;

  @Test
  void getAllTest() {
    List<EvolutionModel> result = service.getAll();
    Assertions.assertNotNull(result);
    Assertions.assertEquals(3, result.size());
  }

  @Test
  void getByIdTest() {
    EvolutionModel result = service.getById(TO_CHAMPTION_ID);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(ZERO_OR_MANY, result.getDnaTimes());
  }

  @Test
  void getByIdNotFoundTest() {
    Assertions.assertNull(service.getById(ID_NOT_FOUND));
  }

  @Test
  void saveTest() {
    EvolutionModel result = service.save(EvolutionModel.builder().dnaTimes(TO_ULTRA_MEGA)
        .baseDigimonId(1400L).evolvedDigimonId(1600L).build());
    Assertions.assertNotNull(result);
    Assertions.assertEquals(TO_ULTRA_MEGA, result.getDnaTimes());
  }

  @Test
  void saveNameExistsTest() {
    Assertions.assertThrows(DataIntegrityViolationException.class, () -> service.save(EvolutionModel
        .builder().dnaTimes(ZERO_OR_MANY).baseDigimonId(1400L).evolvedDigimonId(1600L).build()));
  }

  @Test
  void updateTest() {
    EvolutionModel result =
        service.update(TO_MEGA_ID, EvolutionModel.builder().dnaTimes(TO_ULTRA_MEGA).build());
    Assertions.assertNotNull(result);
    Assertions.assertEquals(TO_ULTRA_MEGA, result.getDnaTimes());
  }

  @Test
  void updateNoMatchTest() {
    Assertions.assertNull(service.update(ID_NOT_FOUND, EvolutionModel.builder()
        .dnaTimes(TO_ULTRA_MEGA).baseDigimonId(1400L).evolvedDigimonId(100L).build()));
  }

  @Test
  void patchTest() {
    EvolutionModel result = service.patch(TO_MEGA_ID, EvolutionModel.builder()
        .dnaTimes(TO_ULTRA_MEGA).baseDigimonId(1400L).evolvedDigimonId(1600L).build());
    Assertions.assertNotNull(result);
    Assertions.assertEquals(TO_ULTRA_MEGA, result.getDnaTimes());
  }

  @Test
  void patchNoMatchTest() {
    Assertions.assertNull(service.patch(ID_NOT_FOUND, EvolutionModel.builder()
        .dnaTimes(TO_ULTRA_MEGA).baseDigimonId(1400L).evolvedDigimonId(1600L).build()));
  }

  @Test
  void deleteTest() {
    Assertions.assertEquals(TO_MEGA_ID, service.delete(TO_MEGA_ID));
  }

  @Test
  void deleteNoMatchTest() {
    Assertions.assertNull(service.delete(ID_NOT_FOUND));
  }
}
