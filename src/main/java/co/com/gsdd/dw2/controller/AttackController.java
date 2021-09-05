package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.model.hateoas.AttackModel;
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

	@Override
	public AttackModel defineModelWithLinks(AttackModel model) {
		AttackModel linkedModel = super.defineModelWithLinks(model);
		Link linkType = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(AttackTypeController.class).getById(model.getAttackTypeId()))
				.withRel("attackType");
		linkedModel.add(linkType);
		return linkedModel;
	}

}
