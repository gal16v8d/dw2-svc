package com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gsdd.dw2.model.DigimonModel;
import com.gsdd.dw2.persistence.entities.Digimon;
import com.gsdd.dw2.service.AbstractService;
import com.gsdd.dw2.service.DigimonService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api("Digimon CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/digimons")
public class DigimonController extends AbstractController<Digimon, DigimonModel> {

  private final DigimonService digimonService;

  @Override
  public Long getId(DigimonModel model) {
    return model.getDigimonId();
  }

  @Override
  public AbstractService<Digimon, DigimonModel> getService() {
    return digimonService;
  }

}
