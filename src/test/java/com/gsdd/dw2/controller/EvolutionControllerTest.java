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
import com.gsdd.dw2.model.EvolutionModel;
import com.gsdd.dw2.service.EvolutionService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
// @WebMvcTest(EvolutionController.class)
class EvolutionControllerTest {

  private static final String V1_EVOLUTIONS = "/api/evolutions";
  private static final String V1_EVOLUTIONS_1 = V1_EVOLUTIONS + "/1";
  private static final String JSON_PATH_NAME = "$.dnaTimes";
  private static final String MAX_DNA = "20+";
  private static final String MIN_DNA = "0+";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  @Autowired
  private MockMvc mvc;
  @MockBean
  private EvolutionService evolutionService;

  @Test
  void getAllTest() throws Exception {
    willReturn(
        Arrays.asList(
            EvolutionModel.builder()
                .baseDigimonId(1L)
                .evolvedDigimonId(2L)
                .dnaTimes(MIN_DNA)
                .build())).given(evolutionService).getAll();
    mvc.perform(get(V1_EVOLUTIONS).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void getByIdTest() throws Exception {
    willReturn(
        EvolutionModel.builder().baseDigimonId(1L).evolvedDigimonId(2L).dnaTimes(MIN_DNA).build())
            .given(evolutionService)
            .getById(anyLong());
    mvc.perform(get(V1_EVOLUTIONS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(MIN_DNA));
  }

  @Test
  void saveTest() throws Exception {
    EvolutionModel model =
        EvolutionModel.builder().baseDigimonId(1L).evolvedDigimonId(2L).dnaTimes(MAX_DNA).build();
    willReturn(model).given(evolutionService).save(any(EvolutionModel.class));
    mvc.perform(
        post(V1_EVOLUTIONS).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(MAX_DNA));
  }

  @Test
  void saveBadRequestTest() throws Exception {
    EvolutionModel model = EvolutionModel.builder().build();
    mvc.perform(
        post(V1_EVOLUTIONS).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateTest() throws Exception {
    EvolutionModel model =
        EvolutionModel.builder().baseDigimonId(1L).evolvedDigimonId(2L).dnaTimes(MAX_DNA).build();
    willReturn(model).given(evolutionService).update(anyLong(), any(EvolutionModel.class));
    mvc.perform(
        put(V1_EVOLUTIONS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(MAX_DNA));
  }

  @Test
  void updateNotFoundTest() throws Exception {
    EvolutionModel model =
        EvolutionModel.builder().baseDigimonId(1L).evolvedDigimonId(2L).dnaTimes(MAX_DNA).build();
    willReturn(null).given(evolutionService).update(anyLong(), any(EvolutionModel.class));
    mvc.perform(
        put(V1_EVOLUTIONS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isNotFound());
  }

  @Test
  void patchTest() throws Exception {
    EvolutionModel model = EvolutionModel.builder().dnaTimes(MAX_DNA).build();
    willReturn(
        EvolutionModel.builder().baseDigimonId(1L).evolvedDigimonId(2L).dnaTimes(MAX_DNA).build())
            .given(evolutionService)
            .patch(anyLong(), any(EvolutionModel.class));
    mvc.perform(
        patch(V1_EVOLUTIONS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(MAX_DNA));
  }

  @Test
  void patchNotFoundTest() throws Exception {
    EvolutionModel model =
        EvolutionModel.builder().baseDigimonId(1L).evolvedDigimonId(2L).dnaTimes(MAX_DNA).build();
    willReturn(null).given(evolutionService).patch(anyLong(), any(EvolutionModel.class));
    mvc.perform(
        patch(V1_EVOLUTIONS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteTest() throws Exception {
    willReturn(1L).given(evolutionService).delete(anyLong());
    mvc.perform(delete(V1_EVOLUTIONS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteNotFoundTest() throws Exception {
    willReturn(null).given(evolutionService).delete(anyLong());
    mvc.perform(delete(V1_EVOLUTIONS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }
}
