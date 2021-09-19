package co.com.gsdd.dw2.service.it;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import co.com.gsdd.dw2.model.hateoas.DigimonTypeModel;
import co.com.gsdd.dw2.service.DigimonTypeService;

@DataJpaTest
class DigimonTypeServiceIT {

	private static final long DATA_ID = 200L;
	private static final long VIRUS_ID = 400L;
	private static final long ID_NOT_FOUND = 500L;
	private static final String ZOMBIE = "Zombie";
	private static final String DATA = "Data";
	@Autowired
	private DigimonTypeService service;

	@Test
	void getAllTest() {
		List<DigimonTypeModel> result = service.getAll();
		Assertions.assertNotNull(result);
		Assertions.assertEquals(3, result.size());
	}

	@Test
	void getByIdTest() {
		DigimonTypeModel result = service.getById(DATA_ID);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(DATA, result.getName());
	}

	@Test
	void getByIdNotFoundTest() {
		Assertions.assertNull(service.getById(ID_NOT_FOUND));
	}

	@Test
	void saveTest() {
		DigimonTypeModel result = service.save(DigimonTypeModel.builder().name(ZOMBIE).build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ZOMBIE, result.getName());
	}

	@Test
	void saveNameExistsTest() {
		Assertions.assertThrows(DataIntegrityViolationException.class,
				() -> service.save(DigimonTypeModel.builder().name(DATA).build()));
	}

	@Test
	void updateTest() {
		DigimonTypeModel result = service.update(VIRUS_ID, DigimonTypeModel.builder().name(ZOMBIE).build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ZOMBIE, result.getName());
	}

	@Test
	void updateNoMatchTest() {
		Assertions.assertNull(service.update(ID_NOT_FOUND, DigimonTypeModel.builder().name(ZOMBIE).build()));
	}

	@Test
	void patchTest() {
		DigimonTypeModel result = service.patch(VIRUS_ID, DigimonTypeModel.builder().name(ZOMBIE).build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ZOMBIE, result.getName());
	}

	@Test
	void patchNoMatchTest() {
		Assertions.assertNull(service.patch(ID_NOT_FOUND, DigimonTypeModel.builder().name(ZOMBIE).build()));
	}

	@Test
	void deleteTest() {
		Assertions.assertEquals(VIRUS_ID, service.delete(VIRUS_ID));
	}

	@Test
	void deleteNoMatchTest() {
		Assertions.assertNull(service.delete(ID_NOT_FOUND));
	}
}
