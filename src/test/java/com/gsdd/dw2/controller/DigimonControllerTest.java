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
import com.gsdd.dw2.model.DigimonModel;
import com.gsdd.dw2.service.DigimonService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
// @WebMvcTest(DigimonController.class)
class DigimonControllerTest {

  private static final String V1_DIGIMON = "/api/digimons";
  private static final String V1_DIGIMON_1 = V1_DIGIMON + "/1";
  private static final String JSON_PATH_NAME = "$.name";
  private static final String METALGREYMON = "MetalGreymon";
  private static final String AGUMON = "Agumon";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  @Autowired
  private MockMvc mvc;
  @MockBean
  private DigimonService digimonService;

  @Test
  void getAllTest() throws Exception {
    willReturn(
        Collections.singletonList(
            DigimonModel.builder()
                .digimonId(1L)
                .elementId(1L)
                .digimonTypeId(1L)
                .levelId(1L)
                .name(AGUMON)
                .build())).given(digimonService).getAll();
    mvc.perform(get(V1_DIGIMON).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void getByIdTest() throws Exception {
    willReturn(
        DigimonModel.builder()
            .digimonId(1L)
            .elementId(1L)
            .digimonTypeId(1L)
            .levelId(1L)
            .name(AGUMON)
            .build()).given(digimonService).getById(anyLong());
    mvc.perform(get(V1_DIGIMON_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(AGUMON));
  }

  @Test
  void saveTest() throws Exception {
    DigimonModel model = DigimonModel.builder()
        .elementId(1L)
        .digimonTypeId(1L)
        .levelId(1L)
        .name(METALGREYMON)
        .build();
    willReturn(model).given(digimonService).save(any(DigimonModel.class));
    mvc.perform(
        post(V1_DIGIMON).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(METALGREYMON));
  }

  @Test
  void saveBadRequestTest() throws Exception {
    DigimonModel model = DigimonModel.builder().build();
    mvc.perform(
        post(V1_DIGIMON).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateTest() throws Exception {
    DigimonModel model = DigimonModel.builder()
        .elementId(1L)
        .digimonTypeId(1L)
        .levelId(1L)
        .name(METALGREYMON)
        .build();
    willReturn(model).given(digimonService).update(anyLong(), any(DigimonModel.class));
    mvc.perform(
        put(V1_DIGIMON_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(METALGREYMON));
  }

  @Test
  void updateNotFoundTest() throws Exception {
    DigimonModel model = DigimonModel.builder()
        .elementId(1L)
        .digimonTypeId(1L)
        .levelId(1L)
        .name(METALGREYMON)
        .build();
    willReturn(null).given(digimonService).update(anyLong(), any(DigimonModel.class));
    mvc.perform(
        put(V1_DIGIMON_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isNotFound());
  }

  @Test
  void patchTest() throws Exception {
    DigimonModel model = DigimonModel.builder().levelId(1L).name(METALGREYMON).build();

    willReturn(
        DigimonModel.builder()
            .elementId(1L)
            .digimonTypeId(1L)
            .levelId(1L)
            .name(METALGREYMON)
            .build()).given(digimonService).patch(anyLong(), any(DigimonModel.class));
    mvc.perform(
        patch(V1_DIGIMON_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(METALGREYMON));
  }

  @Test
  void patchNotFoundTest() throws Exception {
    DigimonModel model = DigimonModel.builder().name(METALGREYMON).build();
    willReturn(null).given(digimonService).patch(anyLong(), any(DigimonModel.class));
    mvc.perform(
        patch(V1_DIGIMON_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteTest() throws Exception {
    willReturn(1L).given(digimonService).delete(anyLong());
    mvc.perform(delete(V1_DIGIMON_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteNotFoundTest() throws Exception {
    willReturn(null).given(digimonService).delete(anyLong());
    mvc.perform(delete(V1_DIGIMON_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }
}
