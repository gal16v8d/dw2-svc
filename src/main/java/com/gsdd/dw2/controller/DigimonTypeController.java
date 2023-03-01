package com.gsdd.dw2.controller;

import com.gsdd.dw2.model.DigimonTypeModel;
import com.gsdd.dw2.persistence.entities.DigimonType;
import com.gsdd.dw2.service.BaseService;
import com.gsdd.dw2.service.DigimonTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Digimon Type CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("api/digimonTypes")
public class DigimonTypeController implements BaseController<DigimonType, DigimonTypeModel> {

  private final DigimonTypeService digimonTypeService;

  @Override
  public Long getId(DigimonTypeModel model) {
    return model.getDigimonTypeId();
  }

  @Override
  public BaseService<DigimonType, DigimonTypeModel> getService() {
    return digimonTypeService;
  }
}
