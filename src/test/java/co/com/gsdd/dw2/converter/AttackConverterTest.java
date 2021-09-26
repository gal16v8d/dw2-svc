package co.com.gsdd.dw2.converter;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.gsdd.dw2.model.AttackModel;
import co.com.gsdd.dw2.persistence.entities.Attack;
import co.com.gsdd.dw2.persistence.entities.AttackType;
import co.com.gsdd.dw2.repository.AttackTypeRepository;

@ExtendWith(MockitoExtension.class)
class AttackConverterTest {

	private static final String ASSIST = "Assist";
	private static final String NECRO_MAGIC = "Necro Magic";
	private AttackConverter converter;
	@Mock
	private AttackTypeRepository repo;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		converter = BDDMockito.spy(new AttackConverter(repo));
	}

	private Attack arrangeAttack() {
		return Attack.builder().attackId(1L).mp(0).name(NECRO_MAGIC)
				.attackType(AttackType.builder().attackTypeId(1L).name(ASSIST).build()).build();
	}

	@Test
	void convertToDomainNullTest() {
		Assertions.assertNull(converter.convertToDomain(null));
	}

	@Test
	void convertToDomainTest() {
		AttackModel model = converter.convertToDomain(arrangeAttack());
		Assertions.assertAll(() -> Assertions.assertNotNull(model), () -> Assertions.assertEquals(0, model.getMp()),
				() -> Assertions.assertEquals(1L, model.getAttackId()),
				() -> Assertions.assertEquals(NECRO_MAGIC, model.getName()),
				() -> Assertions.assertEquals(1L, model.getAttackTypeId()));
	}

	private AttackModel arrangeAttackModel() {
		return AttackModel.builder().attackId(1L).mp(0).name(NECRO_MAGIC).attackTypeId(1L).build();
	}

	@Test
	void convertToEntityNullTest() {
		Assertions.assertNull(converter.convertToEntity(null));
	}

	@Test
	void convertToEntityTest() {
		BDDMockito.willReturn(Optional.ofNullable(AttackType.builder().attackTypeId(1L).build())).given(repo)
				.findById(BDDMockito.eq(1L));
		Attack entity = converter.convertToEntity(arrangeAttackModel());
		Assertions.assertAll(() -> Assertions.assertNotNull(entity), () -> Assertions.assertEquals(0, entity.getMp()),
				() -> Assertions.assertEquals(1L, entity.getAttackId()),
				() -> Assertions.assertEquals(NECRO_MAGIC, entity.getName()),
				() -> Assertions.assertEquals(1L, entity.getAttackType().getAttackTypeId()));
	}
}
