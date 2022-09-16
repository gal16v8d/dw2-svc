package com.gsdd.dw2.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willReturn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsdd.dw2.model.AttackModel;
import com.gsdd.dw2.service.AttackService;
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
// @WebMvcTest(AttackController.class)
class AttackControllerTest {

  private static final String V1_ATTACK = "/api/attacks";
  private static final String V1_ATTACK_1 = V1_ATTACK + "/1";
  private static final String JSON_PATH_NAME = "$.name";
  private static final String SMILEY_BOMB = "Smiley Bomb";
  private static final String NECRO_MAGIC = "Necro Magic";
  private static final ObjectMapper MAPPER = new ObjectMapper();
  @Autowired private MockMvc mvc;
  @MockBean private AttackService attackService;

  @Test
  void getAllTest() throws Exception {
    willReturn(
            Arrays.asList(
                AttackModel.builder()
                    .attackTypeId(1L)
                    .attackTargetTypeId(1L)
                    .attackId(1L)
                    .mp(0)
                    .name(NECRO_MAGIC)
                    .build()))
        .given(attackService)
        .getAll();
    mvc.perform(MockMvcRequestBuilders.get(V1_ATTACK).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
  }

  @Test
  void getByIdTest() throws Exception {
    willReturn(
            AttackModel.builder()
                .attackTypeId(1L)
                .attackTargetTypeId(1L)
                .attackId(1L)
                .mp(0)
                .name(NECRO_MAGIC)
                .build())
        .given(attackService)
        .getById(anyLong());
    mvc.perform(
            MockMvcRequestBuilders.get(V1_ATTACK_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(NECRO_MAGIC));
  }

  @Test
  void saveTest() throws Exception {
    AttackModel model =
        AttackModel.builder()
            .attackTypeId(1L)
            .attackTargetTypeId(1L)
            .attackId(1L)
            .mp(16)
            .name(SMILEY_BOMB)
            .build();
    willReturn(model).given(attackService).save(any(AttackModel.class));
    mvc.perform(
            MockMvcRequestBuilders.post(V1_ATTACK)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(SMILEY_BOMB));
  }

  @Test
  void saveBadRequestTest() throws Exception {
    AttackModel model = AttackModel.builder().build();
    mvc.perform(
            MockMvcRequestBuilders.post(V1_ATTACK)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void updateTest() throws Exception {
    AttackModel model =
        AttackModel.builder()
            .attackTypeId(1L)
            .attackTargetTypeId(1L)
            .attackId(1L)
            .mp(16)
            .name(SMILEY_BOMB)
            .build();
    willReturn(model).given(attackService).update(anyLong(), any(AttackModel.class));
    mvc.perform(
            MockMvcRequestBuilders.put(V1_ATTACK_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(SMILEY_BOMB));
  }

  @Test
  void updateNotFoundTest() throws Exception {
    AttackModel model =
        AttackModel.builder()
            .attackTypeId(1L)
            .attackTargetTypeId(1L)
            .attackId(1L)
            .mp(16)
            .name(SMILEY_BOMB)
            .build();
    willReturn(null).given(attackService).update(anyLong(), any(AttackModel.class));
    mvc.perform(
            MockMvcRequestBuilders.put(V1_ATTACK_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void patchTest() throws Exception {
    AttackModel model = AttackModel.builder().mp(16).name(SMILEY_BOMB).build();
    willReturn(AttackModel.builder().attackTypeId(1L).attackId(1L).mp(16).name(SMILEY_BOMB).build())
        .given(attackService)
        .patch(anyLong(), any(AttackModel.class));
    mvc.perform(
            MockMvcRequestBuilders.patch(V1_ATTACK_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(SMILEY_BOMB));
  }

  @Test
  void patchNotFoundTest() throws Exception {
    AttackModel model = AttackModel.builder().name(SMILEY_BOMB).build();
    willReturn(null).given(attackService).patch(anyLong(), any(AttackModel.class));
    mvc.perform(
            MockMvcRequestBuilders.patch(V1_ATTACK_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(model)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void deleteTest() throws Exception {
    willReturn(1L).given(attackService).delete(anyLong());
    mvc.perform(
            MockMvcRequestBuilders.delete(V1_ATTACK_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  void deleteNotFoundTest() throws Exception {
    willReturn(null).given(attackService).delete(anyLong());
    mvc.perform(
            MockMvcRequestBuilders.delete(V1_ATTACK_1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
