package co.com.gsdd.dw2.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.hateoas.AttackModel;
import co.com.gsdd.dw2.persistence.entities.Attack;
import co.com.gsdd.dw2.persistence.entities.AttackType;
import co.com.gsdd.dw2.repository.AttackRepository;

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
		service = BDDMockito.spy(new AttackService(attackRepository, attackConverter));
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
		BDDMockito.willReturn(Arrays.asList(Attack.builder().attackId(2L).build())).given(attackRepository)
				.findAll(BDDMockito.any(Sort.class));
		BDDMockito.willReturn(AttackModel.builder().attackId(2L).build()).given(attackConverter)
				.convertToDomain(BDDMockito.any(Attack.class));
		List<AttackModel> resultList = service.getAll();
		Assertions.assertNotNull(resultList);
		Assertions.assertEquals(1, resultList.size());
	}

	@Test
	void getByIdTest() {
		BDDMockito.willReturn(Optional.ofNullable(Attack.builder().attackId(2L).build())).given(attackRepository)
				.findById(BDDMockito.anyLong());
		BDDMockito.willReturn(AttackModel.builder().attackId(2L).attackTypeId(1L).mp(0).name(NECRO_MAGIC).build())
				.given(attackConverter).convertToDomain(BDDMockito.any(Attack.class));
		AttackModel result = service.getById(2L);
		Assertions.assertNotNull(result);
		Assertions.assertAll(() -> Assertions.assertEquals(0, result.getMp()),
				() -> Assertions.assertEquals(2L, result.getAttackId()),
				() -> Assertions.assertEquals(NECRO_MAGIC, result.getName()),
				() -> Assertions.assertEquals(1L, result.getAttackTypeId()));
	}

	@Test
	void getByIdNotFoundTest() {
		BDDMockito.willReturn(Optional.ofNullable(null)).given(attackRepository).findById(BDDMockito.anyLong());
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
		BDDMockito.willReturn(entity).given(attackRepository).saveAndFlush(BDDMockito.any(Attack.class));
		BDDMockito.willReturn(entity).given(attackConverter).convertToEntity(BDDMockito.any(AttackModel.class));
		BDDMockito.willReturn(model).given(attackConverter).convertToDomain(BDDMockito.any(Attack.class));
		AttackModel result = service.save(model);
		Assertions.assertNotNull(result);
		Assertions.assertAll(() -> Assertions.assertEquals(0, result.getMp()),
				() -> Assertions.assertNull(result.getAttackId()),
				() -> Assertions.assertEquals(NECRO_MAGIC, result.getName()),
				() -> Assertions.assertEquals(1L, result.getAttackTypeId()));
	}
}
