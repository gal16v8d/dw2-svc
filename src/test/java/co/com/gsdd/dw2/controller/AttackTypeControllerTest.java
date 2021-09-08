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

import co.com.gsdd.dw2.model.hateoas.AttackTypeModel;
import co.com.gsdd.dw2.service.AttackTypeService;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(AttackTypeController.class)
class AttackTypeControllerTest {

	private static final String V1_ATTACK_TYPES = "/v1/attackTypes";
	private static final String V1_ATTACK_TYPES_1 = "/v1/attackTypes/1";
	private static final String JSON_PATH_LINKS = "$._links";
	private static final String JSON_PATH_NAME = "$.name";
	private static final String RUN = "Run";
	private static final String APPLICATION_HAL_JSON = "application/hal+json";
	private static final String ATTACK = "Attack";
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Autowired
	private MockMvc mvc;
	@MockBean
	private AttackTypeService attackTypeService;

	@Test
	void getAllTest() throws Exception {
		BDDMockito.willReturn(Arrays.asList(AttackTypeModel.builder().attackTypeId(1L).name(ATTACK).build()))
				.given(attackTypeService).getAll();
		mvc.perform(MockMvcRequestBuilders.get(V1_ATTACK_TYPES).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded").exists())
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.attackTypeModelList").isArray());
	}

	@Test
	void getByIdTest() throws Exception {
		BDDMockito.willReturn(AttackTypeModel.builder().attackTypeId(1L).name(ATTACK).build()).given(attackTypeService)
				.getById(BDDMockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.get(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(ATTACK))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
	}

	@Test
	void saveTest() throws Exception {
		AttackTypeModel model = AttackTypeModel.builder().name(RUN).build();
		BDDMockito.willReturn(AttackTypeModel.builder().attackTypeId(1L).name(RUN).build()).given(attackTypeService)
				.save(BDDMockito.any(AttackTypeModel.class));
		mvc.perform(MockMvcRequestBuilders.post(V1_ATTACK_TYPES).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(RUN))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
	}

	@Test
	void saveBadRequestTest() throws Exception {
		AttackTypeModel model = AttackTypeModel.builder().build();
		mvc.perform(MockMvcRequestBuilders.post(V1_ATTACK_TYPES).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void updateTest() throws Exception {
		AttackTypeModel model = AttackTypeModel.builder().name(RUN).build();
		BDDMockito.willReturn(AttackTypeModel.builder().attackTypeId(1L).name(RUN).build()).given(attackTypeService)
				.update(BDDMockito.anyLong(), BDDMockito.any(AttackTypeModel.class));
		mvc.perform(MockMvcRequestBuilders.put(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(RUN))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
	}

	@Test
	void updateNotFoundTest() throws Exception {
		AttackTypeModel model = AttackTypeModel.builder().name(RUN).build();
		BDDMockito.willReturn(null).given(attackTypeService).update(BDDMockito.anyLong(),
				BDDMockito.any(AttackTypeModel.class));
		mvc.perform(MockMvcRequestBuilders.put(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void patchTest() throws Exception {
		AttackTypeModel model = AttackTypeModel.builder().name(RUN).build();
		BDDMockito.willReturn(AttackTypeModel.builder().attackTypeId(1L).name(RUN).build()).given(attackTypeService)
				.patch(BDDMockito.anyLong(), BDDMockito.any(AttackTypeModel.class));
		mvc.perform(MockMvcRequestBuilders.patch(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(RUN))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
	}

	@Test
	void patchNotFoundTest() throws Exception {
		AttackTypeModel model = AttackTypeModel.builder().name(RUN).build();
		BDDMockito.willReturn(null).given(attackTypeService).patch(BDDMockito.anyLong(),
				BDDMockito.any(AttackTypeModel.class));
		mvc.perform(MockMvcRequestBuilders.patch(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void deleteTest() throws Exception {
		BDDMockito.willReturn(1L).given(attackTypeService).delete(BDDMockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.delete(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	void deleteNotFoundTest() throws Exception {
		BDDMockito.willReturn(null).given(attackTypeService).delete(BDDMockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.delete(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
