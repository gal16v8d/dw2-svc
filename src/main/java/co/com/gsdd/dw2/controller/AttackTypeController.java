package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.model.hateoas.AttackTypeModel;
import co.com.gsdd.dw2.persistence.entities.AttackType;
import co.com.gsdd.dw2.service.AbstractService;
import co.com.gsdd.dw2.service.AttackTypeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api("Attack Type CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/attackTypes")
public class AttackTypeController extends AbstractController<AttackType, AttackTypeModel> {

	private final AttackTypeService attackTypeService;
	
	@Override
	public Long getId(AttackTypeModel model) {
		return model.getAttackTypeId();
	}

	@Override
	public AbstractService<AttackType, AttackTypeModel> getService() {
		return attackTypeService;
	}

}
