package com.gsdd.dw2.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.spy;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import com.gsdd.dw2.converter.GenericConverter;
import com.gsdd.dw2.model.AttackModel;
import com.gsdd.dw2.persistence.entities.Attack;
import com.gsdd.dw2.persistence.entities.AttackType;
import com.gsdd.dw2.repository.AttackRepository;

@ExtendWith(MockitoExtension.class)
class AttackServiceTest {

  private static final String NECRO_MAGIC = "Necro Magic";
  private AttackService service;
  @Mock
  private AttackRepository attackRepository;
  @Mock
  private GenericConverter<Attack, AttackModel> attackConverter;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    service = spy(new AttackService(attackRepository, attackConverter));
  }

  @Test
  void getSortArgTest() {
    Assertions.assertEquals("attackId", service.getSortArg());
  }

  @Test
  void replaceIdTest() {
    Attack newEntity = Attack.builder().attackId(2L).build();
    Attack oldEntity = Attack.builder().attackId(1L).build();
    Assertions.assertEquals(1L, service.replaceId(newEntity, oldEntity).getAttackId());
  }

  @Test
  void getAllTest() {
    willReturn(Arrays.asList(Attack.builder().attackId(2L).build())).given(attackRepository)
        .findAll(any(Sort.class));
    willReturn(AttackModel.builder().attackId(2L).build()).given(attackConverter)
        .convertToDomain(any(Attack.class));
    List<AttackModel> resultList = service.getAll();
    Assertions.assertNotNull(resultList);
    Assertions.assertEquals(1, resultList.size());
  }

  @Test
  void getByIdTest() {
    willReturn(Optional.ofNullable(Attack.builder().attackId(2L).build())).given(attackRepository)
        .findById(anyLong());
    willReturn(AttackModel.builder().attackId(2L).attackTypeId(1L).mp(0).name(NECRO_MAGIC).build())
        .given(attackConverter).convertToDomain(any(Attack.class));
    AttackModel result = service.getById(2L);
    Assertions.assertNotNull(result);
    Assertions.assertAll(() -> Assertions.assertEquals(0, result.getMp()),
        () -> Assertions.assertEquals(2L, result.getAttackId()),
        () -> Assertions.assertEquals(NECRO_MAGIC, result.getName()),
        () -> Assertions.assertEquals(1L, result.getAttackTypeId()));
  }

  @Test
  void getByIdNotFoundTest() {
    willReturn(Optional.ofNullable(null)).given(attackRepository).findById(anyLong());
    AttackModel result = service.getById(2L);
    Assertions.assertNull(result);
  }

  @Test
  void saveInvalidModelTest() {
    Assertions.assertNull(service.save(null));
  }

  @Test
  void saveTest() {
    AttackModel model = AttackModel.builder().attackTypeId(1L).mp(0).name(NECRO_MAGIC).build();
    Attack entity = Attack.builder().attackType(AttackType.builder().attackTypeId(1L).build()).mp(0)
        .name(NECRO_MAGIC).build();
    willReturn(entity).given(attackRepository).saveAndFlush(any(Attack.class));
    willReturn(entity).given(attackConverter).convertToEntity(any(AttackModel.class));
    willReturn(model).given(attackConverter).convertToDomain(any(Attack.class));
    AttackModel result = service.save(model);
    Assertions.assertNotNull(result);
    Assertions.assertAll(() -> Assertions.assertEquals(0, result.getMp()),
        () -> Assertions.assertNull(result.getAttackId()),
        () -> Assertions.assertEquals(NECRO_MAGIC, result.getName()),
        () -> Assertions.assertEquals(1L, result.getAttackTypeId()));
  }

  @Test
  void updateNotFoundTest() {
    willReturn(Optional.ofNullable(null)).given(attackRepository).findById(anyLong());
    Assertions.assertNull(service.update(1L, AttackModel.builder().build()));
  }

  @Test
  void patchNotFoundTest() {
    willReturn(Optional.ofNullable(null)).given(attackRepository).findById(anyLong());
    Assertions.assertNull(service.patch(1L, AttackModel.builder().build()));
  }

  @Test
  void deleteNotFoundTest() {
    willReturn(Optional.ofNullable(null)).given(attackRepository).findById(anyLong());
    Assertions.assertNull(service.delete(1L));
  }

  @Test
  void deleteTest() {
    willReturn(Optional.ofNullable(Attack.builder().build())).given(attackRepository)
        .findById(anyLong());
    willDoNothing().given(attackRepository).delete(any(Attack.class));
    Assertions.assertNotNull(service.delete(1L));
  }

}
