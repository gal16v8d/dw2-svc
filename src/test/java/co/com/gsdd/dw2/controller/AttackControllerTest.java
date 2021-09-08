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

import co.com.gsdd.dw2.model.hateoas.AttackModel;
import co.com.gsdd.dw2.service.AttackService;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(AttackController.class)
class AttackControllerTest {

	private static final String V1_ATTACK = "/v1/attacks";
	private static final String V1_ATTACK_1 = "/v1/attacks/1";
	private static final String JSON_PATH_LINKS = "$._links";
	private static final String JSON_PATH_NAME = "$.name";
	private static final String SMILEY_BOMB = "Smiley Bomb";
	private static final String APPLICATION_HAL_JSON = "application/hal+json";
	private static final String NECRO_MAGIC = "Necro Magic";
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Autowired
	private MockMvc mvc;
	@MockBean
	private AttackService attackService;

	@Test
	void getAllTest() throws Exception {
		BDDMockito
				.willReturn(Arrays
						.asList(AttackModel.builder().attackTypeId(1L).attackId(1L).mp(0).name(NECRO_MAGIC).build()))
				.given(attackService).getAll();
		mvc.perform(MockMvcRequestBuilders.get(V1_ATTACK).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded").exists())
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.attackModelList").isArray());
	}

	@Test
	void getByIdTest() throws Exception {
		BDDMockito.willReturn(AttackModel.builder().attackTypeId(1L).attackId(1L).mp(0).name(NECRO_MAGIC).build())
				.given(attackService).getById(BDDMockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.get(V1_ATTACK_1).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(NECRO_MAGIC))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists())
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS + ".attackType").exists());
	}

	@Test
	void saveTest() throws Exception {
		AttackModel model = AttackModel.builder().attackTypeId(1L).attackId(1L).mp(16).name(SMILEY_BOMB).build();
		BDDMockito.willReturn(AttackModel.builder().attackTypeId(1L).attackId(1L).mp(16).name(SMILEY_BOMB).build())
				.given(attackService).save(BDDMockito.any(AttackModel.class));
		mvc.perform(MockMvcRequestBuilders.post(V1_ATTACK).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(SMILEY_BOMB))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
	}

	@Test
	void saveBadRequestTest() throws Exception {
		AttackModel model = AttackModel.builder().build();
		mvc.perform(MockMvcRequestBuilders.post(V1_ATTACK).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void updateTest() throws Exception {
		AttackModel model = AttackModel.builder().attackTypeId(1L).attackId(1L).mp(16).name(SMILEY_BOMB).build();
		BDDMockito.willReturn(AttackModel.builder().attackTypeId(1L).attackId(1L).mp(16).name(SMILEY_BOMB).build())
				.given(attackService).update(BDDMockito.anyLong(), BDDMockito.any(AttackModel.class));
		mvc.perform(MockMvcRequestBuilders.put(V1_ATTACK_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(SMILEY_BOMB))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
	}

	@Test
	void updateNotFoundTest() throws Exception {
		AttackModel model = AttackModel.builder().attackTypeId(1L).attackId(1L).mp(16).name(SMILEY_BOMB).build();
		BDDMockito.willReturn(null).given(attackService).update(BDDMockito.anyLong(),
				BDDMockito.any(AttackModel.class));
		mvc.perform(MockMvcRequestBuilders.put(V1_ATTACK_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void patchTest() throws Exception {
		AttackModel model = AttackModel.builder().attackTypeId(1L).attackId(1L).mp(16).name(SMILEY_BOMB).build();
		BDDMockito.willReturn(AttackModel.builder().attackTypeId(1L).attackId(1L).mp(16).name(SMILEY_BOMB).build())
				.given(attackService).patch(BDDMockito.anyLong(), BDDMockito.any(AttackModel.class));
		mvc.perform(MockMvcRequestBuilders.patch(V1_ATTACK_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(SMILEY_BOMB))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists());
	}

	@Test
	void patchNotFoundTest() throws Exception {
		AttackModel model = AttackModel.builder().name(SMILEY_BOMB).build();
		BDDMockito.willReturn(null).given(attackService).patch(BDDMockito.anyLong(), BDDMockito.any(AttackModel.class));
		mvc.perform(MockMvcRequestBuilders.patch(V1_ATTACK_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void deleteTest() throws Exception {
		BDDMockito.willReturn(1L).given(attackService).delete(BDDMockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.delete(V1_ATTACK_1).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	void deleteNotFoundTest() throws Exception {
		BDDMockito.willReturn(null).given(attackService).delete(BDDMockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.delete(V1_ATTACK_1).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
