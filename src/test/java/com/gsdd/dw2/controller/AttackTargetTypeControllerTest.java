package com.gsdd.dw2.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willReturn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsdd.dw2.model.AttackTargetTypeModel;
import com.gsdd.dw2.service.AttackTargetTypeService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
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
// @WebMvcTest(AttackTypeController.class)
class AttackTargetTypeControllerTest {

  private static final String V1_ATTACK_TYPES = "/api/attackTargetTypes";
  private static final String V1_ATTACK_TYPES_1 = V1_ATTACK_TYPES + "/1";
  private static final String JSON_PATH_NAME = "$.name";
  private static final String RANDOM_ALLY = "Random Ally";
  private static final String ALL = "All";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  @Autowired private MockMvc mvc;
  @MockBean private AttackTargetTypeService attackTargetTypeService;

  @Test
  void getAllTest() throws Exception {
    willReturn(
            Arrays.asList(AttackTargetTypeModel.builder().attackTargetTypeId(1L).name(ALL).build()))
        .given(attackTargetTypeService)
        .getAll();
    mvc.perform(
            MockMvcRequestBuilders.get(V1_ATTACK_TYPES)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
  }

  @Test
  void getByIdTest() throws Exception {
    willReturn(AttackTargetTypeModel.builder().attackTargetTypeId(1L).name(ALL).build())
        .given(attackTargetTypeService)
        .getById(anyLong());
    mvc.perform(
            MockMvcRequestBuilders.get(V1_ATTACK_TYPES_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(ALL));
  }

  @Test
  void saveTest() throws Exception {
    AttackTargetTypeModel model =
        AttackTargetTypeModel.builder().attackTargetTypeId(1L).name(RANDOM_ALLY).build();
    willReturn(model).given(attackTargetTypeService).save(any(AttackTargetTypeModel.class));
    mvc.perform(
            MockMvcRequestBuilders.post(V1_ATTACK_TYPES)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(RANDOM_ALLY));
  }

  @Test
  void saveBadRequestTest() throws Exception {
    AttackTargetTypeModel model = AttackTargetTypeModel.builder().build();
    mvc.perform(
            MockMvcRequestBuilders.post(V1_ATTACK_TYPES)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void updateTest() throws Exception {
    AttackTargetTypeModel model =
        AttackTargetTypeModel.builder().attackTargetTypeId(1L).name(RANDOM_ALLY).build();
    willReturn(model)
        .given(attackTargetTypeService)
        .update(anyLong(), any(AttackTargetTypeModel.class));
    mvc.perform(
            MockMvcRequestBuilders.put(V1_ATTACK_TYPES_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(RANDOM_ALLY));
  }

  @Test
  void updateNotFoundTest() throws Exception {
    AttackTargetTypeModel model =
        AttackTargetTypeModel.builder().attackTargetTypeId(1L).name(RANDOM_ALLY).build();
    willReturn(null)
        .given(attackTargetTypeService)
        .update(anyLong(), any(AttackTargetTypeModel.class));
    mvc.perform(
            MockMvcRequestBuilders.put(V1_ATTACK_TYPES_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void patchTest() throws Exception {
    AttackTargetTypeModel model = AttackTargetTypeModel.builder().name(RANDOM_ALLY).build();
    willReturn(AttackTargetTypeModel.builder().attackTargetTypeId(1L).name(RANDOM_ALLY).build())
        .given(attackTargetTypeService)
        .patch(anyLong(), any(AttackTargetTypeModel.class));
    mvc.perform(
            MockMvcRequestBuilders.patch(V1_ATTACK_TYPES_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(RANDOM_ALLY));
  }

  @Test
  void patchNotFoundTest() throws Exception {
    AttackTargetTypeModel model = AttackTargetTypeModel.builder().name(RANDOM_ALLY).build();
    willReturn(null)
        .given(attackTargetTypeService)
        .patch(anyLong(), any(AttackTargetTypeModel.class));
    mvc.perform(
            MockMvcRequestBuilders.patch(V1_ATTACK_TYPES_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void deleteTest() throws Exception {
    willReturn(1L).given(attackTargetTypeService).delete(anyLong());
    mvc.perform(
            MockMvcRequestBuilders.delete(V1_ATTACK_TYPES_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  void deleteNotFoundTest() throws Exception {
    willReturn(null).given(attackTargetTypeService).delete(anyLong());
    mvc.perform(
            MockMvcRequestBuilders.delete(V1_ATTACK_TYPES_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
