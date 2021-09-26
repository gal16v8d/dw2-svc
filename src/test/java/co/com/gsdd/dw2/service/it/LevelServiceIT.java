package co.com.gsdd.dw2.service.it;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import co.com.gsdd.dw2.model.LevelModel;
import co.com.gsdd.dw2.service.LevelService;

@DataJpaTest
class LevelServiceIT {

	private static final long ROOKIE_ID = 200L;
	private static final long MEGA_ID = 500L;
	private static final long ID_NOT_FOUND = 600L;
	private static final String MEGA_DNA = "Mega DNA";
	private static final String ROOKIE = "Rookie";
	@Autowired
	private LevelService service;
	
	@Test
	void getAllTest() {
		List<LevelModel> result = service.getAll();
		Assertions.assertNotNull(result);
		Assertions.assertEquals(4, result.size());
	}

	@Test
	void getByIdTest() {
		LevelModel result = service.getById(ROOKIE_ID);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(ROOKIE, result.getName());
	}

	@Test
	void getByIdNotFoundTest() {
		Assertions.assertNull(service.getById(ID_NOT_FOUND));
	}

	@Test
	void saveTest() {
		LevelModel result = service.save(LevelModel.builder().name(MEGA_DNA).build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(MEGA_DNA, result.getName());
	}

	@Test
	void saveNameExistsTest() {
		Assertions.assertThrows(DataIntegrityViolationException.class,
				() -> service.save(LevelModel.builder().name(ROOKIE).build()));
	}

	@Test
	void updateTest() {
		LevelModel result = service.update(MEGA_ID, LevelModel.builder().name(MEGA_DNA).build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(MEGA_DNA, result.getName());
	}

	@Test
	void updateNoMatchTest() {
		Assertions.assertNull(service.update(ID_NOT_FOUND, LevelModel.builder().name(MEGA_DNA).build()));
	}

	@Test
	void patchTest() {
		LevelModel result = service.patch(MEGA_ID, LevelModel.builder().name(MEGA_DNA).build());
		Assertions.assertNotNull(result);
		Assertions.assertEquals(MEGA_DNA, result.getName());
	}

	@Test
	void patchNoMatchTest() {
		Assertions.assertNull(service.patch(ID_NOT_FOUND, LevelModel.builder().name(MEGA_DNA).build()));
	}

	@Test
	void deleteTest() {
		Assertions.assertEquals(MEGA_ID, service.delete(MEGA_ID));
	}

	@Test
	void deleteNoMatchTest() {
		Assertions.assertNull(service.delete(ID_NOT_FOUND));
	}
}
