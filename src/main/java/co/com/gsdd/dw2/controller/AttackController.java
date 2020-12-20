package co.com.gsdd.dw2.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class AttackController {

	private final AttackRepository attackRepository;
	private final GenericConverter<Attack, AttackModel> attackConverter;

	@Autowired
	public AttackController(AttackRepository attackRepository, GenericConverter<Attack, AttackModel> attackConverter) {
		this.attackConverter = attackConverter;
		this.attackRepository = attackRepository;
	}

	private AttackModel defineModelWithLinks(Attack entity) {
		AttackModel model = attackConverter.convertToDomain(entity);
		Link link = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(AttackController.class).getById(model.getAttackId())).withSelfRel();
		Link linkType = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(AttackTypeController.class).getById(model.getAttackTypeId()))
				.withRel("attackType");
		model.add(link, linkType);
		return model;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<AttackModel>> getAll() {
		List<AttackModel> attacks = attackRepository.findAll(Sort.by("attackId")).stream()
				.map(this::defineModelWithLinks).collect(Collectors.toList());
		Link link = WebMvcLinkBuilder.linkTo(AttackController.class).withSelfRel();
		CollectionModel<AttackModel> result = CollectionModel.of(attacks, link);
		return ResponseEntity.ok(result);
	}

	@GetMapping("{attackId:[0-9]+}")
	public ResponseEntity<AttackModel> getById(@PathVariable("attackId") Long attackId) {
		return attackRepository.findById(attackId).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<AttackModel> save(@Valid @RequestBody AttackModel model) {
		Attack newAttack = attackConverter.convertToEntity(model);
		return Optional.ofNullable(newAttack).map(attackRepository::saveAndFlush).map(this::defineModelWithLinks)
				.map(ResponseEntity::ok).orElseThrow(AttackTypeNotFoundException::new);
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

	@DeleteMapping("{attackId:[0-9]+}")
	public ResponseEntity<Object> delete(@PathVariable("attackId") Long attackId) {
		return attackRepository.findById(attackId).map((Attack atk) -> {
			attackRepository.delete(atk);
			return ResponseEntity.noContent().build();
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
