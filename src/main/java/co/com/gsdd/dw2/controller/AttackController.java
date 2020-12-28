package co.com.gsdd.dw2.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.exception.AttackTypeNotFoundException;
import co.com.gsdd.dw2.model.AttackModel;
import co.com.gsdd.dw2.persistence.entities.Attack;
import co.com.gsdd.dw2.repository.AttackRepository;

@RefreshScope
@RestController
@RequestMapping("v1/attacks")
public class AttackController extends AbstractController<Attack, AttackModel> {

	private final AttackRepository attackRepository;
	private final GenericConverter<Attack, AttackModel> attackConverter;

	@Autowired
	public AttackController(AttackRepository attackRepository, GenericConverter<Attack, AttackModel> attackConverter) {
		this.attackConverter = attackConverter;
		this.attackRepository = attackRepository;
	}
	
	@Override
	public String getSortArg() {
		return "attackId";
	}

	@Override
	public Long getId(Attack entity) {
		return entity.getAttackId();
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

	@PutMapping("{attackId:[0-9]+}")
	public ResponseEntity<AttackModel> update(@PathVariable("attackId") Long attackId,
			@Valid @RequestBody AttackModel model) {
		return attackRepository.findById(attackId).map((Attack dbEntity) -> {
			Attack atk = attackConverter.convertToEntity(model);
			return Optional.ofNullable(atk).map((Attack a) -> {
				a.setAttackId(dbEntity.getAttackId());
				return attackRepository.saveAndFlush(a);
			}).orElseThrow(AttackTypeNotFoundException::new);
		}).map(this::defineModelWithLinks).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
