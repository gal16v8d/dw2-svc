package com.gsdd.dw2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsdd.dw2.model.DigimonTypeModel;
import com.gsdd.dw2.service.DigimonTypeService;
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
// @WebMvcTest(DigimonTypeController.class)
class DigimonTypeControllerTest {

    private static final String V1_DIGIMON_TYPES = "/v1/digimonTypes";
    private static final String V1_DIGIMON_TYPES_1 = "/v1/digimonTypes/1";
    private static final String JSON_PATH_NAME = "$.name";
    private static final String EXTRA = "Extra";
    private static final String DATA = "Data";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired private MockMvc mvc;
    @MockBean private DigimonTypeService digimonTypeService;

    @Test
    void getAllTest() throws Exception {
        BDDMockito.willReturn(
                        Arrays.asList(
                                DigimonTypeModel.builder().digimonTypeId(1L).name(DATA).build()))
                .given(digimonTypeService)
                .getAll();
        mvc.perform(
                        MockMvcRequestBuilders.get(V1_DIGIMON_TYPES)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content()
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getByIdTest() throws Exception {
        BDDMockito.willReturn(DigimonTypeModel.builder().digimonTypeId(1L).name(DATA).build())
                .given(digimonTypeService)
                .getById(BDDMockito.anyLong());
        mvc.perform(
                        MockMvcRequestBuilders.get(V1_DIGIMON_TYPES_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content()
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(DATA));
    }

    @Test
    void saveTest() throws Exception {
        DigimonTypeModel model = DigimonTypeModel.builder().digimonTypeId(1L).name(EXTRA).build();
        BDDMockito.willReturn(model)
                .given(digimonTypeService)
                .save(BDDMockito.any(DigimonTypeModel.class));
        mvc.perform(
                        MockMvcRequestBuilders.post(V1_DIGIMON_TYPES)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(model)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content()
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(EXTRA));
    }

    @Test
    void saveBadRequestTest() throws Exception {
        DigimonTypeModel model = DigimonTypeModel.builder().build();
        mvc.perform(
                        MockMvcRequestBuilders.post(V1_DIGIMON_TYPES)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(model)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateTest() throws Exception {
        DigimonTypeModel model = DigimonTypeModel.builder().digimonTypeId(1L).name(EXTRA).build();
        BDDMockito.willReturn(model)
                .given(digimonTypeService)
                .update(BDDMockito.anyLong(), BDDMockito.any(DigimonTypeModel.class));
        mvc.perform(
                        MockMvcRequestBuilders.put(V1_DIGIMON_TYPES_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(model)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content()
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(EXTRA));
    }

    @Test
    void updateNotFoundTest() throws Exception {
        DigimonTypeModel model = DigimonTypeModel.builder().name(EXTRA).build();
        BDDMockito.willReturn(null)
                .given(digimonTypeService)
                .update(BDDMockito.anyLong(), BDDMockito.any(DigimonTypeModel.class));
        mvc.perform(
                        MockMvcRequestBuilders.put(V1_DIGIMON_TYPES_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(model)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void patchTest() throws Exception {
        DigimonTypeModel model = DigimonTypeModel.builder().name(EXTRA).build();
        BDDMockito.willReturn(DigimonTypeModel.builder().digimonTypeId(1L).name(EXTRA).build())
                .given(digimonTypeService)
                .patch(BDDMockito.anyLong(), BDDMockito.any(DigimonTypeModel.class));
        mvc.perform(
                        MockMvcRequestBuilders.patch(V1_DIGIMON_TYPES_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(model)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content()
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath(JSON_PATH_NAME).value(EXTRA));
    }

    @Test
    void patchNotFoundTest() throws Exception {
        DigimonTypeModel model = DigimonTypeModel.builder().name(EXTRA).build();
        BDDMockito.willReturn(null)
                .given(digimonTypeService)
                .patch(BDDMockito.anyLong(), BDDMockito.any(DigimonTypeModel.class));
        mvc.perform(
                        MockMvcRequestBuilders.patch(V1_DIGIMON_TYPES_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(model)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteTest() throws Exception {
        BDDMockito.willReturn(1L).given(digimonTypeService).delete(BDDMockito.anyLong());
        mvc.perform(
                        MockMvcRequestBuilders.delete(V1_DIGIMON_TYPES_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteNotFoundTest() throws Exception {
        BDDMockito.willReturn(null).given(digimonTypeService).delete(BDDMockito.anyLong());
        mvc.perform(
                        MockMvcRequestBuilders.delete(V1_DIGIMON_TYPES_1)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
