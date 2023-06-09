package com.gsdd.dw2.controller;

import com.gsdd.dw2.model.EvolutionModel;
import com.gsdd.dw2.persistence.entities.Evolution;
import com.gsdd.dw2.service.BaseService;
import com.gsdd.dw2.service.EvolutionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Evolution CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("api/evolutions")
public class EvolutionController implements BaseController<Evolution, EvolutionModel> {

  private final EvolutionService evolutionService;

  @Override
  public Long getId(EvolutionModel model) {
    return model.getEvolutionId();
  }

  @Override
  public BaseService<Evolution, EvolutionModel> getService() {
    return evolutionService;
  }
}
