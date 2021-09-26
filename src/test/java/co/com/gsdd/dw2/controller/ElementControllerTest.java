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

import co.com.gsdd.dw2.model.ElementModel;
import co.com.gsdd.dw2.service.ElementService;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(ElementController.class)
class ElementControllerTest {

	private static final String V1_ELEMENTS = "/v1/elements";
	private static final String V1_ELEMENTS_1 = "/v1/elements/1";
	private static final String JSON_PATH_NAME = "$.name";
	private static final String AIR = "Air";
	private static final String NONE = "None";
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Autowired
	private MockMvc mvc;
	@MockBean
	private ElementService elementService;

	@Test
	void getAllTest() throws Exception {
		BDDMockito.willReturn(Arrays.asList(ElementModel.builder().elementId(1L).name(NONE).build()))
				.given(elementService).getAll();
		mvc.perform(MockMvcRequestBuilders.get(V1_ELEMENTS).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
	}

	@Test
	void getByIdTest() throws Exception {
		BDDMockito.willReturn(ElementModel.builder().elementId(1L).name(NONE).build()).given(elementService)
				.getById(BDDMockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.get(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(NONE));
	}

	@Test
	void saveTest() throws Exception {
		ElementModel model = ElementModel.builder().elementId(1L).name(AIR).build();
		BDDMockito.willReturn(model).given(elementService).save(BDDMockito.any(ElementModel.class));
		mvc.perform(MockMvcRequestBuilders.post(V1_ELEMENTS).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(AIR));
	}

	@Test
	void saveBadRequestTest() throws Exception {
		ElementModel model = ElementModel.builder().build();
		mvc.perform(MockMvcRequestBuilders.post(V1_ELEMENTS).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void updateTest() throws Exception {
		ElementModel model = ElementModel.builder().elementId(1L).name(AIR).build();
		BDDMockito.willReturn(model).given(elementService).update(BDDMockito.anyLong(),
				BDDMockito.any(ElementModel.class));
		mvc.perform(MockMvcRequestBuilders.put(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(AIR));
	}

	@Test
	void updateNotFoundTest() throws Exception {
		ElementModel model = ElementModel.builder().name(AIR).build();
		BDDMockito.willReturn(null).given(elementService).update(BDDMockito.anyLong(),
				BDDMockito.any(ElementModel.class));
		mvc.perform(MockMvcRequestBuilders.put(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void patchTest() throws Exception {
		ElementModel model = ElementModel.builder().name(AIR).build();
		BDDMockito.willReturn(ElementModel.builder().elementId(1L).name(AIR).build()).given(elementService)
				.patch(BDDMockito.anyLong(), BDDMockito.any(ElementModel.class));
		mvc.perform(MockMvcRequestBuilders.patch(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(AIR));
	}

	@Test
	void patchNotFoundTest() throws Exception {
		ElementModel model = ElementModel.builder().name(AIR).build();
		BDDMockito.willReturn(null).given(elementService).patch(BDDMockito.anyLong(),
				BDDMockito.any(ElementModel.class));
		mvc.perform(MockMvcRequestBuilders.patch(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(MAPPER.writeValueAsString(model))).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void deleteTest() throws Exception {
		BDDMockito.willReturn(1L).given(elementService).delete(BDDMockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.delete(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	void deleteNotFoundTest() throws Exception {
		BDDMockito.willReturn(null).given(elementService).delete(BDDMockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.delete(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
