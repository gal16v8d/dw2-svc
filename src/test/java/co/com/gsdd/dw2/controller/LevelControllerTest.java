package co.com.gsdd.dw2.controller;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.gsdd.dw2.model.hateoas.LevelModel;
import co.com.gsdd.dw2.service.LevelService;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(LevelController.class)
class LevelControllerTest {

	private static final String V1_LEVELS = "/v1/levels";
	private static final String V1_LEVELS_1 = "/v1/levels/1";
	private static final String JSON_PATH_LINKS = "$._links";
	private static final String JSON_PATH_NAME = "$.name";
	private static final String MEGA_DNA = "Mega DNA";
	private static final String APPLICATION_HAL_JSON = "application/hal+json";
	private static final String ROOKIE = "Rookie";
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Autowired
	private MockMvc mvc;
	@MockBean
	private LevelService levelService;

	@Test
	void getAllTest() throws Exception {
		BDDMockito.willReturn(Arrays.asList(LevelModel.builder().levelId(1L).name(ROOKIE).build())).given(levelService)
				.getAll();
		mvc.perform(MockMvcRequestBuilders.get(V1_LEVELS).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded").exists())
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.levelModelList").isArray());
	}

	@Test
	void getByIdTest() throws Exception {
		BDDMockito.willReturn(LevelModel.builder().levelId(1L).name(ROOKIE).build()).given(levelService)
				.getById(BDDMockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.get(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(ROOKIE))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
	}

	@Test
	void saveTest() throws Exception {
		LevelModel model = LevelModel.builder().name(MEGA_DNA).build();
		BDDMockito.willReturn(LevelModel.builder().levelId(1L).name(MEGA_DNA).build()).given(levelService)
				.save(BDDMockito.any(LevelModel.class));
		mvc.perform(MockMvcRequestBuilders.post(V1_LEVELS).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(MEGA_DNA))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
	}

	@Test
	void saveBadRequestTest() throws Exception {
		LevelModel model = LevelModel.builder().build();
		mvc.perform(MockMvcRequestBuilders.post(V1_LEVELS).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void updateTest() throws Exception {
		LevelModel model = LevelModel.builder().name(MEGA_DNA).build();
		BDDMockito.willReturn(LevelModel.builder().levelId(1L).name(MEGA_DNA).build()).given(levelService)
				.update(BDDMockito.anyLong(), BDDMockito.any(LevelModel.class));
		mvc.perform(MockMvcRequestBuilders.put(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(MEGA_DNA))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
	}

	@Test
	void updateNotFoundTest() throws Exception {
		LevelModel model = LevelModel.builder().name(MEGA_DNA).build();
		BDDMockito.willReturn(null).given(levelService).update(BDDMockito.anyLong(), BDDMockito.any(LevelModel.class));
		mvc.perform(MockMvcRequestBuilders.put(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void patchTest() throws Exception {
		LevelModel model = LevelModel.builder().name(MEGA_DNA).build();
		BDDMockito.willReturn(LevelModel.builder().levelId(1L).name(MEGA_DNA).build()).given(levelService)
				.patch(BDDMockito.anyLong(), BDDMockito.any(LevelModel.class));
		mvc.perform(MockMvcRequestBuilders.patch(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(MEGA_DNA))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
	}

	@Test
	void patchNotFoundTest() throws Exception {
		LevelModel model = LevelModel.builder().name(MEGA_DNA).build();
		BDDMockito.willReturn(null).given(levelService).patch(BDDMockito.anyLong(), BDDMockito.any(LevelModel.class));
		mvc.perform(MockMvcRequestBuilders.patch(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void deleteTest() throws Exception {
		BDDMockito.willReturn(1L).given(levelService).delete(BDDMockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.delete(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	void deleteNotFoundTest() throws Exception {
		BDDMockito.willReturn(null).given(levelService).delete(BDDMockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.delete(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
