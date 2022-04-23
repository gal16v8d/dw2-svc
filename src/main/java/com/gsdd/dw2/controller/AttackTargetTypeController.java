package com.gsdd.dw2.controller;

import com.gsdd.dw2.model.AttackTargetTypeModel;
import com.gsdd.dw2.persistence.entities.AttackTargetType;
import com.gsdd.dw2.service.AbstractService;
import com.gsdd.dw2.service.AttackTargetTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Attack Target Type CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/attackTargetTypes")
public class AttackTargetTypeController
        extends AbstractController<AttackTargetType, AttackTargetTypeModel> {

    private final AttackTargetTypeService attackTargetTypeService;

    @Override
    public Long getId(AttackTargetTypeModel model) {
        return model.getAttackTargetTypeId();
    }

    @Override
    public AbstractService<AttackTargetType, AttackTargetTypeModel> getService() {
        return attackTargetTypeService;
    }
}
