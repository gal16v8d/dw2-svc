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
import com.gsdd.dw2.model.AttackTypeModel;
import com.gsdd.dw2.service.AttackTypeService;
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
// @WebMvcTest(AttackTypeController.class)
class AttackTypeControllerTest {

  private static final String V1_ATTACK_TYPES = "/api/attackTypes";
  private static final String V1_ATTACK_TYPES_1 = V1_ATTACK_TYPES + "/1";
  private static final String JSON_PATH_NAME = "$.name";
  private static final String RUN = "Run";
  private static final String ATTACK = "Attack";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  @Autowired
  private MockMvc mvc;
  @MockBean
  private AttackTypeService attackTypeService;

  @Test
  void getAllTest() throws Exception {
    willReturn(Arrays.asList(AttackTypeModel.builder().attackTypeId(1L).name(ATTACK).build()))
        .given(attackTypeService)
        .getAll();
    mvc.perform(get(V1_ATTACK_TYPES).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void getByIdTest() throws Exception {
    willReturn(AttackTypeModel.builder().attackTypeId(1L).name(ATTACK).build())
        .given(attackTypeService)
        .getById(anyLong());
    mvc.perform(get(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(ATTACK));
  }

  @Test
  void saveTest() throws Exception {
    AttackTypeModel model = AttackTypeModel.builder().attackTypeId(1L).name(RUN).build();
    willReturn(model).given(attackTypeService).save(any(AttackTypeModel.class));
    mvc.perform(
        post(V1_ATTACK_TYPES).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(RUN));
  }

  @Test
  void saveBadRequestTest() throws Exception {
    AttackTypeModel model = AttackTypeModel.builder().build();
    mvc.perform(
        post(V1_ATTACK_TYPES).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateTest() throws Exception {
    AttackTypeModel model = AttackTypeModel.builder().attackTypeId(1L).name(RUN).build();
    willReturn(model).given(attackTypeService).update(anyLong(), any(AttackTypeModel.class));
    mvc.perform(
        put(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(RUN));
  }

  @Test
  void updateNotFoundTest() throws Exception {
    AttackTypeModel model = AttackTypeModel.builder().name(RUN).build();
    willReturn(null).given(attackTypeService).update(anyLong(), any(AttackTypeModel.class));
    mvc.perform(
        put(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isNotFound());
  }

  @Test
  void patchTest() throws Exception {
    AttackTypeModel model = AttackTypeModel.builder().name(RUN).build();
    willReturn(AttackTypeModel.builder().attackTypeId(1L).name(RUN).build())
        .given(attackTypeService)
        .patch(anyLong(), any(AttackTypeModel.class));
    mvc.perform(
        patch(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(RUN));
  }

  @Test
  void patchNotFoundTest() throws Exception {
    AttackTypeModel model = AttackTypeModel.builder().name(RUN).build();
    willReturn(null).given(attackTypeService).patch(anyLong(), any(AttackTypeModel.class));
    mvc.perform(
        patch(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteTest() throws Exception {
    willReturn(1L).given(attackTypeService).delete(anyLong());
    mvc.perform(delete(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteNotFoundTest() throws Exception {
    willReturn(null).given(attackTypeService).delete(anyLong());
    mvc.perform(delete(V1_ATTACK_TYPES_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }
}
