package com.gsdd.dw2.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsdd.dw2.model.DigimonTypeModel;
import com.gsdd.dw2.service.DigimonTypeService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
// @WebMvcTest(DigimonTypeController.class)
class DigimonTypeControllerTest {

  private static final String V1_DIGIMON_TYPES = "/api/digimonTypes";
  private static final String V1_DIGIMON_TYPES_1 = V1_DIGIMON_TYPES + "/1";
  private static final String JSON_PATH_NAME = "$.name";
  private static final String EXTRA = "Extra";
  private static final String DATA = "Data";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  @Autowired
  private MockMvc mvc;
  @MockBean
  private DigimonTypeService digimonTypeService;

  @Test
  void getAllTest() throws Exception {
    BDDMockito
        .willReturn(Arrays.asList(DigimonTypeModel.builder().digimonTypeId(1L).name(DATA).build()))
        .given(digimonTypeService)
        .getAll();
    mvc.perform(get(V1_DIGIMON_TYPES).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void getByIdTest() throws Exception {
    willReturn(DigimonTypeModel.builder().digimonTypeId(1L).name(DATA).build())
        .given(digimonTypeService)
        .getById(anyLong());
    mvc.perform(get(V1_DIGIMON_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(DATA));
  }

  @Test
  void saveTest() throws Exception {
    DigimonTypeModel model = DigimonTypeModel.builder().digimonTypeId(1L).name(EXTRA).build();
    willReturn(model).given(digimonTypeService).save(any(DigimonTypeModel.class));
    mvc.perform(
        post(V1_DIGIMON_TYPES).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(EXTRA));
  }

  @Test
  void saveBadRequestTest() throws Exception {
    DigimonTypeModel model = DigimonTypeModel.builder().build();
    mvc.perform(
        post(V1_DIGIMON_TYPES).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateTest() throws Exception {
    DigimonTypeModel model = DigimonTypeModel.builder().digimonTypeId(1L).name(EXTRA).build();
    willReturn(model).given(digimonTypeService).update(anyLong(), any(DigimonTypeModel.class));
    mvc.perform(
        put(V1_DIGIMON_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(EXTRA));
  }

  @Test
  void updateNotFoundTest() throws Exception {
    DigimonTypeModel model = DigimonTypeModel.builder().name(EXTRA).build();
    willReturn(null).given(digimonTypeService).update(anyLong(), any(DigimonTypeModel.class));
    mvc.perform(
        put(V1_DIGIMON_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isNotFound());
  }

  @Test
  void patchTest() throws Exception {
    DigimonTypeModel model = DigimonTypeModel.builder().name(EXTRA).build();
    willReturn(DigimonTypeModel.builder().digimonTypeId(1L).name(EXTRA).build())
        .given(digimonTypeService)
        .patch(anyLong(), any(DigimonTypeModel.class));
    mvc.perform(
        patch(V1_DIGIMON_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(EXTRA));
  }

  @Test
  void patchNotFoundTest() throws Exception {
    DigimonTypeModel model = DigimonTypeModel.builder().name(EXTRA).build();
    willReturn(null).given(digimonTypeService).patch(anyLong(), any(DigimonTypeModel.class));
    mvc.perform(
        patch(V1_DIGIMON_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteTest() throws Exception {
    willReturn(1L).given(digimonTypeService).delete(anyLong());
    mvc.perform(delete(V1_DIGIMON_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteNotFoundTest() throws Exception {
    willReturn(null).given(digimonTypeService).delete(anyLong());
    mvc.perform(delete(V1_DIGIMON_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }
}
