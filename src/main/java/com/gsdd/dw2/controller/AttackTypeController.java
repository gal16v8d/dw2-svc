package com.gsdd.dw2.controller;

import com.gsdd.dw2.model.AttackTypeModel;
import com.gsdd.dw2.persistence.entities.AttackType;
import com.gsdd.dw2.service.AttackTypeService;
import com.gsdd.dw2.service.BaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Attack Type CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("api/attackTypes")
public class AttackTypeController implements BaseController<AttackType, AttackTypeModel> {

  private final AttackTypeService attackTypeService;

  @Override
  public Long getId(AttackTypeModel model) {
    return model.getAttackTypeId();
  }

  @Override
  public BaseService<AttackType, AttackTypeModel> getService() {
    return attackTypeService;
  }
}
