package com.gsdd.dw2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsdd.dw2.model.DigimonModel;
import com.gsdd.dw2.service.DigimonService;
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

@SpringBootTest
@AutoConfigureMockMvc
// @WebMvcTest(DigimonController.class)
class DigimonControllerTest {

  private static final String V1_DIGIMON = "/v1/digimons";
  private static final String V1_DIGIMON_1 = "/v1/digimons/1";
  private static final String JSON_PATH_NAME = "$.name";
  private static final String METALGREYMON = "MetalGreymon";
  private static final String AGUMON = "Agumon";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  @Autowired private MockMvc mvc;
  @MockBean private DigimonService digimonService;

  @Test
  void getAllTest() throws Exception {
    BDDMockito.willReturn(
            Arrays.asList(
                DigimonModel.builder()
                    .digimonId(1L)
                    .elementId(1L)
                    .digimonTypeId(1L)
                    .levelId(1L)
                    .name(AGUMON)
                    .build()))
        .given(digimonService)
        .getAll();
    mvc.perform(
            MockMvcRequestBuilders.get(V1_DIGIMON).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
  }

  @Test
  void getByIdTest() throws Exception {
    BDDMockito.willReturn(
            DigimonModel.builder()
                .digimonId(1L)
                .elementId(1L)
                .digimonTypeId(1L)
                .levelId(1L)
                .name(AGUMON)
                .build())
        .given(digimonService)
        .getById(BDDMockito.anyLong());
    mvc.perform(
            MockMvcRequestBuilders.get(V1_DIGIMON_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(AGUMON));
  }

  @Test
  void saveTest() throws Exception {
    DigimonModel model =
        DigimonModel.builder()
            .elementId(1L)
            .digimonTypeId(1L)
            .levelId(1L)
            .name(METALGREYMON)
            .build();
    BDDMockito.willReturn(model).given(digimonService).save(BDDMockito.any(DigimonModel.class));
    mvc.perform(
            MockMvcRequestBuilders.post(V1_DIGIMON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(METALGREYMON));
  }

  @Test
  void saveBadRequestTest() throws Exception {
    DigimonModel model = DigimonModel.builder().build();
    mvc.perform(
            MockMvcRequestBuilders.post(V1_DIGIMON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void updateTest() throws Exception {
    DigimonModel model =
        DigimonModel.builder()
            .elementId(1L)
            .digimonTypeId(1L)
            .levelId(1L)
            .name(METALGREYMON)
            .build();
    BDDMockito.willReturn(model)
        .given(digimonService)
        .update(BDDMockito.anyLong(), BDDMockito.any(DigimonModel.class));
    mvc.perform(
            MockMvcRequestBuilders.put(V1_DIGIMON_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(METALGREYMON));
  }

  @Test
  void updateNotFoundTest() throws Exception {
    DigimonModel model =
        DigimonModel.builder()
            .elementId(1L)
            .digimonTypeId(1L)
            .levelId(1L)
            .name(METALGREYMON)
            .build();
    BDDMockito.willReturn(null)
        .given(digimonService)
        .update(BDDMockito.anyLong(), BDDMockito.any(DigimonModel.class));
    mvc.perform(
            MockMvcRequestBuilders.put(V1_DIGIMON_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void patchTest() throws Exception {
    DigimonModel model = DigimonModel.builder().levelId(1L).name(METALGREYMON).build();
    BDDMockito.willReturn(
            DigimonModel.builder()
                .elementId(1L)
                .digimonTypeId(1L)
                .levelId(1L)
                .name(METALGREYMON)
                .build())
        .given(digimonService)
        .patch(BDDMockito.anyLong(), BDDMockito.any(DigimonModel.class));
    mvc.perform(
            MockMvcRequestBuilders.patch(V1_DIGIMON_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(METALGREYMON));
  }

  @Test
  void patchNotFoundTest() throws Exception {
    DigimonModel model = DigimonModel.builder().name(METALGREYMON).build();
    BDDMockito.willReturn(null)
        .given(digimonService)
        .patch(BDDMockito.anyLong(), BDDMockito.any(DigimonModel.class));
    mvc.perform(
            MockMvcRequestBuilders.patch(V1_DIGIMON_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void deleteTest() throws Exception {
    BDDMockito.willReturn(1L).given(digimonService).delete(BDDMockito.anyLong());
    mvc.perform(
            MockMvcRequestBuilders.delete(V1_DIGIMON_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  void deleteNotFoundTest() throws Exception {
    BDDMockito.willReturn(null).given(digimonService).delete(BDDMockito.anyLong());
    mvc.perform(
            MockMvcRequestBuilders.delete(V1_DIGIMON_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
