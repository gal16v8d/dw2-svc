package co.com.gsdd.dw2.controller;

import java.util.Arrays;

import org.hamcrest.Matchers;
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

import co.com.gsdd.dw2.model.hateoas.DigimonXAttackModel;
import co.com.gsdd.dw2.service.DigimonXAttackService;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(DigimonXAttackController.class)
class DigimonXAttackControllerTest {

	private static final String JSON_PATH_LINKS = "$._links";
	private static final String APPLICATION_HAL_JSON = "application/hal+json";
	@Autowired
	private MockMvc mvc;
	@MockBean
	private DigimonXAttackService service;

	@Test
	void getAllAtkTest() throws Exception {
		BDDMockito.willReturn(Arrays.asList(DigimonXAttackModel.builder().attackId(1L).digimonId(1L).build()))
				.given(service).getAllAtk(BDDMockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.get("/v1/digimons/1/attacks").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded").exists())
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.digimonXAttackModelList").isArray());
	}

	@Test
	void getByIdTest() throws Exception {
		BDDMockito.willReturn(DigimonXAttackModel.builder().attackId(1L).digimonId(1L).build()).given(service)
				.getById(BDDMockito.anyLong(), BDDMockito.anyLong());
		mvc.perform(
				MockMvcRequestBuilders.get("/v1/digimons/1/attacks/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.digimonId", Matchers.equalTo((Number) 1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.attackId", Matchers.equalTo((Number) 1)));
	}

	@Test
	void getByIdNotFoundTest() throws Exception {
		BDDMockito.willReturn(null).given(service).getById(BDDMockito.anyLong(), BDDMockito.anyLong());
		mvc.perform(
				MockMvcRequestBuilders.get("/v1/digimons/1/attacks/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void associateTest() throws Exception {
		BDDMockito.willReturn(DigimonXAttackModel.builder().attackId(1L).digimonId(1L).build()).given(service)
				.associate(BDDMockito.anyLong(), BDDMockito.anyLong());
		mvc.perform(
				MockMvcRequestBuilders.post("/v1/digimons/1/attacks/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_HAL_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_LINKS).exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.digimonId", Matchers.equalTo((Number) 1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.attackId", Matchers.equalTo((Number) 1)));
	}

	@Test
	void associateNotFoundTest() throws Exception {
		BDDMockito.willReturn(null).given(service).associate(BDDMockito.anyLong(), BDDMockito.anyLong());
		mvc.perform(
				MockMvcRequestBuilders.post("/v1/digimons/1/attacks/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void deassociateTest() throws Exception {
		BDDMockito.willReturn(1L).given(service).deassociate(BDDMockito.anyLong(), BDDMockito.anyLong());
		mvc.perform(
				MockMvcRequestBuilders.delete("/v1/digimons/1/attacks/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	void deassociateNotFoundTest() throws Exception {
		BDDMockito.willReturn(null).given(service).deassociate(BDDMockito.anyLong(), BDDMockito.anyLong());
		mvc.perform(
				MockMvcRequestBuilders.delete("/v1/digimons/1/attacks/1").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
