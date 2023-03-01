package com.gsdd.dw2.controller;

import com.gsdd.dw2.model.DigimonModel;
import com.gsdd.dw2.persistence.entities.Digimon;
import com.gsdd.dw2.service.BaseService;
import com.gsdd.dw2.service.DigimonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Digimon CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("api/digimons")
public class DigimonController implements BaseController<Digimon, DigimonModel> {

  private final DigimonService digimonService;

  @Override
  public Long getId(DigimonModel model) {
    return model.getDigimonId();
  }

  @Override
  public BaseService<Digimon, DigimonModel> getService() {
    return digimonService;
  }
}
