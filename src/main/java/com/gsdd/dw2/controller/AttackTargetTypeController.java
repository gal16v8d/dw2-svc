package com.gsdd.dw2.controller;

import com.gsdd.dw2.model.AttackTargetTypeModel;
import com.gsdd.dw2.persistence.entities.AttackTargetType;
import com.gsdd.dw2.service.AttackTargetTypeService;
import com.gsdd.dw2.service.BaseService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Attack Target Type CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("api/attackTargetTypes")
public class AttackTargetTypeController
    implements BaseController<AttackTargetType, AttackTargetTypeModel> {

  private final AttackTargetTypeService attackTargetTypeService;

  @Override
  public Long getId(AttackTargetTypeModel model) {
    return model.getAttackTargetTypeId();
  }

  @Override
  public BaseService<AttackTargetType, AttackTargetTypeModel> getService() {
    return attackTargetTypeService;
  }
}
