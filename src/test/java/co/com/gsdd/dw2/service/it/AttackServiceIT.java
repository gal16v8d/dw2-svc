package co.com.gsdd.dw2.service.it;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import co.com.gsdd.dw2.model.AttackModel;
import co.com.gsdd.dw2.service.AttackService;

@DataJpaTest
class AttackServiceIT {

	private static final long ATTACK_TARGET_ID = 700L;
	private static final long ATTACK_ID = 200L;
	private static final long INTERRUPT_ID = 400L;
	private static final long ID_NOT_FOUND = 600L;
	private static final String RUN = "Run Away";
	private static final String ATTACK = "Blue Blaster";
	@Autowired
	private AttackService service;

	@Test
	void getAllTest() {
		List<AttackModel> result = service.getAll();
		Assertions.assertNotNull(result);
		Assertions.assertEquals(8, result.size());
	}

	@Test
	void getByIdTest() {
		AttackModel result = service.getById(ATTACK_ID);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ATTACK, result.getName());
	}

	@Test
	void getByIdNotFoundTest() {
		Assertions.assertNull(service.getById(ID_NOT_FOUND));
	}

	@Test
	void saveTest() {
		AttackModel result = service.save(AttackModel.builder().name(RUN).mp(0).attackTypeId(ATTACK_ID)
				.attackTargetTypeId(ATTACK_TARGET_ID).build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(RUN, result.getName());
	}

	@Test
	void saveNameExistsTest() {
		Assertions.assertThrows(DataIntegrityViolationException.class, () -> service.save(AttackModel.builder()
				.name(ATTACK).mp(4).attackTypeId(ATTACK_ID).attackTargetTypeId(ATTACK_TARGET_ID).build()));
	}

	@Test
	void updateTest() {
		AttackModel result = service.update(INTERRUPT_ID, AttackModel.builder().name(RUN).mp(0).attackTypeId(ATTACK_ID)
				.attackTargetTypeId(ATTACK_TARGET_ID).build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(RUN, result.getName());
	}

	@Test
	void updateNoMatchTest() {
		Assertions.assertNull(
				service.update(ID_NOT_FOUND, AttackModel.builder().name(RUN).mp(0).attackTypeId(ATTACK_ID).build()));
	}

	@Test
	void patchTest() {
		AttackModel result = service.patch(INTERRUPT_ID,
				AttackModel.builder().name(RUN).mp(0).attackTypeId(ATTACK_ID).build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(RUN, result.getName());
	}

	@Test
	void patchNoMatchTest() {
		Assertions.assertNull(
				service.patch(ID_NOT_FOUND, AttackModel.builder().name(RUN).mp(0).attackTypeId(ATTACK_ID).build()));
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
