package com.gsdd.dw2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsdd.dw2.model.EvolutionModel;
import com.gsdd.dw2.service.EvolutionService;
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
// @WebMvcTest(EvolutionController.class)
class EvolutionControllerTest {

  private static final String V1_EVOLUTIONS = "/v1/evolutions";
  private static final String V1_EVOLUTIONS_1 = "/v1/evolutions/1";
  private static final String JSON_PATH_NAME = "$.dnaTimes";
  private static final String MAX_DNA = "20+";
  private static final String MIN_DNA = "0+";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  @Autowired private MockMvc mvc;
  @MockBean private EvolutionService evolutionService;

  @Test
  void getAllTest() throws Exception {
    BDDMockito.willReturn(
            Arrays.asList(
                EvolutionModel.builder()
                    .baseDigimonId(1L)
                    .evolvedDigimonId(2L)
                    .dnaTimes(MIN_DNA)
                    .build()))
        .given(evolutionService)
        .getAll();
    mvc.perform(
            MockMvcRequestBuilders.get(V1_EVOLUTIONS).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
  }

  @Test
  void getByIdTest() throws Exception {
    BDDMockito.willReturn(
            EvolutionModel.builder()
                .baseDigimonId(1L)
                .evolvedDigimonId(2L)
                .dnaTimes(MIN_DNA)
                .build())
        .given(evolutionService)
        .getById(BDDMockito.anyLong());
    mvc.perform(
            MockMvcRequestBuilders.get(V1_EVOLUTIONS_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(MIN_DNA));
  }

  @Test
  void saveTest() throws Exception {
    EvolutionModel model =
        EvolutionModel.builder().baseDigimonId(1L).evolvedDigimonId(2L).dnaTimes(MAX_DNA).build();
    BDDMockito.willReturn(model).given(evolutionService).save(BDDMockito.any(EvolutionModel.class));
    mvc.perform(
            MockMvcRequestBuilders.post(V1_EVOLUTIONS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(MAX_DNA));
  }

  @Test
  void saveBadRequestTest() throws Exception {
    EvolutionModel model = EvolutionModel.builder().build();
    mvc.perform(
            MockMvcRequestBuilders.post(V1_EVOLUTIONS)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void updateTest() throws Exception {
    EvolutionModel model =
        EvolutionModel.builder().baseDigimonId(1L).evolvedDigimonId(2L).dnaTimes(MAX_DNA).build();
    BDDMockito.willReturn(model)
        .given(evolutionService)
        .update(BDDMockito.anyLong(), BDDMockito.any(EvolutionModel.class));
    mvc.perform(
            MockMvcRequestBuilders.put(V1_EVOLUTIONS_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(MAX_DNA));
  }

  @Test
  void updateNotFoundTest() throws Exception {
    EvolutionModel model =
        EvolutionModel.builder().baseDigimonId(1L).evolvedDigimonId(2L).dnaTimes(MAX_DNA).build();
    BDDMockito.willReturn(null)
        .given(evolutionService)
        .update(BDDMockito.anyLong(), BDDMockito.any(EvolutionModel.class));
    mvc.perform(
            MockMvcRequestBuilders.put(V1_EVOLUTIONS_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void patchTest() throws Exception {
    EvolutionModel model = EvolutionModel.builder().dnaTimes(MAX_DNA).build();
    BDDMockito.willReturn(
            EvolutionModel.builder()
                .baseDigimonId(1L)
                .evolvedDigimonId(2L)
                .dnaTimes(MAX_DNA)
                .build())
        .given(evolutionService)
        .patch(BDDMockito.anyLong(), BDDMockito.any(EvolutionModel.class));
    mvc.perform(
            MockMvcRequestBuilders.patch(V1_EVOLUTIONS_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(MAX_DNA));
  }

  @Test
  void patchNotFoundTest() throws Exception {
    EvolutionModel model =
        EvolutionModel.builder().baseDigimonId(1L).evolvedDigimonId(2L).dnaTimes(MAX_DNA).build();
    BDDMockito.willReturn(null)
        .given(evolutionService)
        .patch(BDDMockito.anyLong(), BDDMockito.any(EvolutionModel.class));
    mvc.perform(
            MockMvcRequestBuilders.patch(V1_EVOLUTIONS_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void deleteTest() throws Exception {
    BDDMockito.willReturn(1L).given(evolutionService).delete(BDDMockito.anyLong());
    mvc.perform(
            MockMvcRequestBuilders.delete(V1_EVOLUTIONS_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  void deleteNotFoundTest() throws Exception {
    BDDMockito.willReturn(null).given(evolutionService).delete(BDDMockito.anyLong());
    mvc.perform(
            MockMvcRequestBuilders.delete(V1_EVOLUTIONS_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
