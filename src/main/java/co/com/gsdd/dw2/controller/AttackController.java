package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.hateoas.AttackModel;
import co.com.gsdd.dw2.persistence.entities.Attack;
import co.com.gsdd.dw2.repository.AttackRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/attacks")
public class AttackController extends AbstractController<Attack, AttackModel> {

	private final AttackRepository attackRepository;
	private final GenericConverter<Attack, AttackModel> attackConverter;
	
	@Override
	public String getSortArg() {
		return "attackId";
	}

	@Override
	public Long getId(Attack entity) {
		return entity.getAttackId();
	}
	
	@Override
	public Attack replaceId(Attack entityNew, Attack entityOrig) {
		entityNew.setAttackId(entityOrig.getAttackId());
		return entityNew;
	}

	@Override
	public JpaRepository<Attack, Long> getRepo() {
		return attackRepository;
	}

	@Override
	public GenericConverter<Attack, AttackModel> getConverter() {
		return attackConverter;
	}

	@Override
	public AttackModel defineModelWithLinks(Attack entity) {
		AttackModel model = super.defineModelWithLinks(entity);
		Link linkType = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(AttackTypeController.class).getById(model.getAttackTypeId()))
				.withRel("attackType");
		model.add(linkType);
		return model;
	}

}
