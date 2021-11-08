package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.model.AttackTargetTypeModel;
import co.com.gsdd.dw2.persistence.entities.AttackTargetType;
import co.com.gsdd.dw2.service.AbstractService;
import co.com.gsdd.dw2.service.AttackTargetTypeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api("Attack Target Type CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/attackTargetTypes")
public class AttackTargetTypeController extends AbstractController<AttackTargetType, AttackTargetTypeModel> {

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
