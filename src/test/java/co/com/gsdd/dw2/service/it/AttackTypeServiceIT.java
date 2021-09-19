package co.com.gsdd.dw2.service.it;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import co.com.gsdd.dw2.model.hateoas.AttackTypeModel;
import co.com.gsdd.dw2.service.AttackTypeService;

@DataJpaTest
class AttackTypeServiceIT {

	private static final long ATTACK_ID = 200L;
	private static final long INTERRUPT_ID = 400L;
	private static final long ID_NOT_FOUND = 600L;
	private static final String RUN = "Run";
	private static final String ATTACK = "Attack";
	@Autowired
	private AttackTypeService service;

	@Test
	void getAllTest() {
		List<AttackTypeModel> result = service.getAll();
		Assertions.assertNotNull(result);
		Assertions.assertEquals(4, result.size());
	}

	@Test
	void getByIdTest() {
		AttackTypeModel result = service.getById(ATTACK_ID);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ATTACK, result.getName());
	}

	@Test
	void getByIdNotFoundTest() {
		Assertions.assertNull(service.getById(ID_NOT_FOUND));
	}

	@Test
	void saveTest() {
		AttackTypeModel result = service.save(AttackTypeModel.builder().name(RUN).build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(RUN, result.getName());
	}

	@Test
	void saveNameExistsTest() {
		Assertions.assertThrows(DataIntegrityViolationException.class,
				() -> service.save(AttackTypeModel.builder().name(ATTACK).build()));
	}

	@Test
	void updateTest() {
		AttackTypeModel result = service.update(INTERRUPT_ID, AttackTypeModel.builder().name(RUN).build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(RUN, result.getName());
	}

	@Test
	void updateNoMatchTest() {
		Assertions.assertNull(service.update(ID_NOT_FOUND, AttackTypeModel.builder().name(RUN).build()));
	}

	@Test
	void patchTest() {
		AttackTypeModel result = service.patch(INTERRUPT_ID, AttackTypeModel.builder().name(RUN).build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(RUN, result.getName());
	}

	@Test
	void patchNoMatchTest() {
		Assertions.assertNull(service.patch(ID_NOT_FOUND, AttackTypeModel.builder().name(RUN).build()));
	}

	@Test
	void deleteTest() {
		Assertions.assertEquals(INTERRUPT_ID, service.delete(INTERRUPT_ID));
	}

	@Test
	void deleteNoMatchTest() {
		Assertions.assertNull(service.delete(ID_NOT_FOUND));
	}
}
