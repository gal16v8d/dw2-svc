package com.gsdd.dw2.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.gsdd.dw2.model.DigimonXAttackModel;
import com.gsdd.dw2.service.DigimonXAttackService;
import java.util.Arrays;
import org.hamcrest.Matchers;
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
// @WebMvcTest(DigimonXAttackController.class)
class DigimonXAttackControllerTest {

  @Autowired
  private MockMvc mvc;
  @MockBean
  private DigimonXAttackService service;

  @Test
  void getAllAtkTest() throws Exception {
    BDDMockito
        .willReturn(Arrays.asList(DigimonXAttackModel.builder().attackId(1L).digimonId(1L).build()))
        .given(service)
        .getAllAtk(anyLong());
    mvc.perform(get("/api/digimons/1/attacks").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void getByIdTest() throws Exception {
    willReturn(DigimonXAttackModel.builder().attackId(1L).digimonId(1L).build()).given(service)
        .getById(anyLong(), anyLong());
    mvc.perform(get("/api/digimons/1/attacks/1").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.digimonId", Matchers.equalTo((Number) 1)))
        .andExpect(jsonPath("$.attackId", Matchers.equalTo((Number) 1)));
  }

  @Test
  void getByIdNotFoundTest() throws Exception {
    willReturn(null).given(service).getById(anyLong(), anyLong());
    mvc.perform(get("/api/digimons/1/attacks/1").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  void associateTest() throws Exception {
    willReturn(DigimonXAttackModel.builder().attackId(1L).digimonId(1L).build()).given(service)
        .associate(anyLong(), anyLong());
    mvc.perform(post("/api/digimons/1/attacks/1").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.digimonId", Matchers.equalTo((Number) 1)))
        .andExpect(jsonPath("$.attackId", Matchers.equalTo((Number) 1)));
  }

  @Test
  void associateNotFoundTest() throws Exception {
    willReturn(null).given(service).associate(anyLong(), anyLong());
    mvc.perform(post("/api/digimons/1/attacks/1").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deassociateTest() throws Exception {
    willReturn(1L).given(service).deassociate(anyLong(), anyLong());
    mvc.perform(delete("/api/digimons/1/attacks/1").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent());
  }

  @Test
  void deassociateNotFoundTest() throws Exception {
    willReturn(null).given(service).deassociate(anyLong(), anyLong());
    mvc.perform(delete("/api/digimons/1/attacks/1").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }
}
