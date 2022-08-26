package com.gsdd.dw2.controller;

import com.gsdd.dw2.model.AttackModel;
import com.gsdd.dw2.persistence.entities.Attack;
import com.gsdd.dw2.service.AbstractService;
import com.gsdd.dw2.service.AttackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Attack CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("api/attacks")
public class AttackController extends AbstractController<Attack, AttackModel> {

  private final AttackService attackService;

  @Override
  public Long getId(AttackModel model) {
    return model.getAttackId();
  }

  @Override
  public AbstractService<Attack, AttackModel> getService() {
    return attackService;
  }
}
