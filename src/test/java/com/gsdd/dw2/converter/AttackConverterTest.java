package com.gsdd.dw2.converter;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.spy;

import com.gsdd.dw2.model.AttackModel;
import com.gsdd.dw2.persistence.entities.Attack;
import com.gsdd.dw2.persistence.entities.AttackTargetType;
import com.gsdd.dw2.persistence.entities.AttackType;
import com.gsdd.dw2.repository.AttackTargetTypeRepository;
import com.gsdd.dw2.repository.AttackTypeRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AttackConverterTest {

    private static final String ASSIST = "Assist";
    private static final String ALL = "All";
    private static final String NECRO_MAGIC = "Necro Magic";
    private AttackConverter converter;
    @Mock private AttackTypeRepository typeRepo;
    @Mock private AttackTargetTypeRepository targetRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        converter = spy(new AttackConverter(typeRepo, targetRepo));
    }

    private Attack arrangeAttack() {
        return Attack.builder()
                .attackId(1L)
                .mp(0)
                .name(NECRO_MAGIC)
                .attackType(AttackType.builder().attackTypeId(1L).name(ASSIST).build())
                .attackTargetType(
                        AttackTargetType.builder().attackTargetTypeId(1L).name(ALL).build())
                .build();
    }

    @Test
    void convertToDomainNullTest() {
        Assertions.assertNull(converter.convertToDomain(null));
    }

    @Test
    void convertToDomainTest() {
        AttackModel model = converter.convertToDomain(arrangeAttack());
        Assertions.assertAll(
                () -> Assertions.assertNotNull(model),
                () -> Assertions.assertEquals(0, model.getMp()),
                () -> Assertions.assertEquals(1L, model.getAttackId()),
                () -> Assertions.assertEquals(NECRO_MAGIC, model.getName()),
                () -> Assertions.assertEquals(1L, model.getAttackTypeId()),
                () -> Assertions.assertEquals(1L, model.getAttackTargetTypeId()));
    }

    private AttackModel arrangeAttackModel() {
        return AttackModel.builder()
                .attackId(1L)
                .mp(0)
                .name(NECRO_MAGIC)
                .attackTypeId(1L)
                .attackTargetTypeId(1L)
                .build();
    }

    @Test
    void convertToEntityNullTest() {
        Assertions.assertNull(converter.convertToEntity(null));
    }

    @Test
    void convertToEntityTest() {
        willReturn(Optional.ofNullable(AttackType.builder().attackTypeId(1L).build()))
                .given(typeRepo)
                .findById(eq(1L));
        willReturn(Optional.ofNullable(AttackTargetType.builder().attackTargetTypeId(1L).build()))
                .given(targetRepo)
                .findById(eq(1L));
        Attack entity = converter.convertToEntity(arrangeAttackModel());
        Assertions.assertAll(
                () -> Assertions.assertNotNull(entity),
                () -> Assertions.assertEquals(0, entity.getMp()),
                () -> Assertions.assertEquals(1L, entity.getAttackId()),
                () -> Assertions.assertEquals(NECRO_MAGIC, entity.getName()),
                () -> Assertions.assertEquals(1L, entity.getAttackType().getAttackTypeId()));
    }
}
