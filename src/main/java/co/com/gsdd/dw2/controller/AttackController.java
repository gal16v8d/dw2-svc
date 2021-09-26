package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.model.AttackModel;
import co.com.gsdd.dw2.persistence.entities.Attack;
import co.com.gsdd.dw2.service.AbstractService;
import co.com.gsdd.dw2.service.AttackService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api("Attack CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/attacks")
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
