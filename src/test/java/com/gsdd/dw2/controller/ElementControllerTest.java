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
import com.gsdd.dw2.model.ElementModel;
import com.gsdd.dw2.service.ElementService;
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
// @WebMvcTest(ElementController.class)
class ElementControllerTest {

  private static final String V1_ELEMENTS = "/api/elements";
  private static final String V1_ELEMENTS_1 = V1_ELEMENTS + "/1";
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
    willReturn(Collections.singletonList(ElementModel.builder().elementId(1L).name(NONE).build()))
        .given(elementService)
        .getAll();
    mvc.perform(get(V1_ELEMENTS).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void getByIdTest() throws Exception {
    willReturn(ElementModel.builder().elementId(1L).name(NONE).build()).given(elementService)
        .getById(anyLong());
    mvc.perform(get(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(NONE));
  }

  @Test
  void saveTest() throws Exception {
    ElementModel model = ElementModel.builder().elementId(1L).name(AIR).build();
    willReturn(model).given(elementService).save(any(ElementModel.class));
    mvc.perform(
        post(V1_ELEMENTS).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(AIR));
  }

  @Test
  void saveBadRequestTest() throws Exception {
    ElementModel model = ElementModel.builder().build();
    mvc.perform(
        post(V1_ELEMENTS).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateTest() throws Exception {
    ElementModel model = ElementModel.builder().elementId(1L).name(AIR).build();
    willReturn(model).given(elementService).update(anyLong(), any(ElementModel.class));
    mvc.perform(
        put(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(AIR));
  }

  @Test
  void updateNotFoundTest() throws Exception {
    ElementModel model = ElementModel.builder().name(AIR).build();
    willReturn(null).given(elementService).update(anyLong(), any(ElementModel.class));
    mvc.perform(
        put(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isNotFound());
  }

  @Test
  void patchTest() throws Exception {
    ElementModel model = ElementModel.builder().name(AIR).build();
    willReturn(ElementModel.builder().elementId(1L).name(AIR).build()).given(elementService)
        .patch(anyLong(), any(ElementModel.class));
    mvc.perform(
        patch(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath(JSON_PATH_NAME).value(AIR));
  }

  @Test
  void patchNotFoundTest() throws Exception {
    ElementModel model = ElementModel.builder().name(AIR).build();
    willReturn(null).given(elementService).patch(anyLong(), any(ElementModel.class));
    mvc.perform(
        patch(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(MAPPER.writeValueAsString(model)))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteTest() throws Exception {
    willReturn(1L).given(elementService).delete(anyLong());
    mvc.perform(delete(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteNotFoundTest() throws Exception {
    willReturn(null).given(elementService).delete(anyLong());
    mvc.perform(delete(V1_ELEMENTS_1).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }
}
