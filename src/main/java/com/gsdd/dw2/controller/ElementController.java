package com.gsdd.dw2.controller;

import com.gsdd.dw2.model.ElementModel;
import com.gsdd.dw2.persistence.entities.Element;
import com.gsdd.dw2.service.BaseService;
import com.gsdd.dw2.service.ElementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Element CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("api/elements")
public class ElementController implements BaseController<Element, ElementModel> {

  private final ElementService elementService;

  @Override
  public Long getId(ElementModel model) {
    return model.getElementId();
  }

  @Override
  public BaseService<Element, ElementModel> getService() {
    return elementService;
  }
}
