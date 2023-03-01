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
import com.gsdd.dw2.model.LevelModel;
import com.gsdd.dw2.service.LevelService;
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
// @WebMvcTest(LevelController.class)
class LevelControllerTest {

  private static final String V1_LEVELS = "/api/levels";
  private static final String V1_LEVELS_1 = V1_LEVELS + "/1";
  private static final String JSON_PATH_NAME = "$.name";
  private static final String MEGA_DNA = "Mega DNA";
  private static final String ROOKIE = "Rookie";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  @Autowired
  private MockMvc mvc;
  @MockBean
  private LevelService levelService;

  @Test
  void getAllTest() throws Exception {
    willReturn(Arrays.asList(LevelModel.builder().levelId(1L).name(ROOKIE).build()))
        .given(levelService)
        .getAll();
    mvc.perform(get(V1_LEVELS).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void getByIdTest() throws Exception {
    willReturn(LevelModel.builder().levelId(1L).name(ROOKIE).build()).given(levelService)
        .getById(anyLong());
    mvc.perform(get(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(ROOKIE));
  }

  @Test
  void saveTest() throws Exception {
    LevelModel model = LevelModel.builder().name(MEGA_DNA).build();
    willReturn(LevelModel.builder().levelId(1L).name(MEGA_DNA).build()).given(levelService)
        .save(any(LevelModel.class));
    mvc.perform(
        post(V1_LEVELS).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(MEGA_DNA));
  }

  @Test
  void saveBadRequestTest() throws Exception {
    LevelModel model = LevelModel.builder().build();
    mvc.perform(
        post(V1_LEVELS).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateTest() throws Exception {
    LevelModel model = LevelModel.builder().name(MEGA_DNA).build();
    willReturn(LevelModel.builder().levelId(1L).name(MEGA_DNA).build()).given(levelService)
        .update(anyLong(), any(LevelModel.class));
    mvc.perform(
        put(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(MEGA_DNA));
  }

  @Test
  void updateNotFoundTest() throws Exception {
    LevelModel model = LevelModel.builder().name(MEGA_DNA).build();
    willReturn(null).given(levelService).update(anyLong(), any(LevelModel.class));
    mvc.perform(
        put(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isNotFound());
  }

  @Test
  void patchTest() throws Exception {
    LevelModel model = LevelModel.builder().name(MEGA_DNA).build();
    willReturn(LevelModel.builder().levelId(1L).name(MEGA_DNA).build()).given(levelService)
        .patch(anyLong(), any(LevelModel.class));
    mvc.perform(
        patch(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(MEGA_DNA));
  }

  @Test
  void patchNotFoundTest() throws Exception {
    LevelModel model = LevelModel.builder().name(MEGA_DNA).build();
    willReturn(null).given(levelService).patch(anyLong(), any(LevelModel.class));
    mvc.perform(
        patch(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteTest() throws Exception {
    willReturn(1L).given(levelService).delete(anyLong());
    mvc.perform(delete(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteNotFoundTest() throws Exception {
    willReturn(null).given(levelService).delete(anyLong());
    mvc.perform(delete(V1_LEVELS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }
}
