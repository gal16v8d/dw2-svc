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
import co.com.gsdd.dw2.model.AttackTypeModel;
import co.com.gsdd.dw2.persistence.entities.AttackType;
import co.com.gsdd.dw2.repository.AttackTypeRepository;

@RefreshScope
@RestController
@RequestMapping("v1/attackTypes")
public class AttackTypeController {

	private final AttackTypeRepository attackTypeRepository;
	private final GenericConverter<AttackType, AttackTypeModel> attackTypeConverter;

	@Autowired
	public AttackTypeController(GenericConverter<AttackType, AttackTypeModel> attackTypeConverter,
			AttackTypeRepository attackTypeRepository) {
		this.attackTypeConverter = attackTypeConverter;
		this.attackTypeRepository = attackTypeRepository;
	}

	private AttackTypeModel defineModelWithLinks(AttackType entity) {
		AttackTypeModel model = attackTypeConverter.convertToDomain(entity);
		Link link = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(AttackTypeController.class).getById(model.getAttackTypeId()))
				.withSelfRel();
		model.add(link);
		return model;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<AttackTypeModel>> getAll() {
		List<AttackTypeModel> attackTypes = attackTypeRepository.findAll(Sort.by("attackTypeId")).stream()
				.map(this::defineModelWithLinks).collect(Collectors.toList());
		Link link = WebMvcLinkBuilder.linkTo(AttackTypeController.class).withSelfRel();
		CollectionModel<AttackTypeModel> result = CollectionModel.of(attackTypes, link);
		return ResponseEntity.ok(result);
	}

	@GetMapping("{attackTypeId:[0-9]+}")
	public ResponseEntity<AttackTypeModel> getById(@PathVariable("attackTypeId") Long attackTypeId) {
		return attackTypeRepository.findById(attackTypeId).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<AttackTypeModel> save(@Valid @RequestBody AttackTypeModel model) {
		return Optional.ofNullable(model).map(attackTypeConverter::convertToEntity)
				.map(attackTypeRepository::saveAndFlush).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PutMapping("{attackTypeId:[0-9]+}")
	public ResponseEntity<AttackTypeModel> update(@PathVariable("attackTypeId") Long attackTypeId,
			@Valid @RequestBody AttackTypeModel model) {
		return attackTypeRepository.findById(attackTypeId).map((AttackType dbEntity) -> {
			AttackType at = attackTypeConverter.convertToEntity(model);
			return Optional.ofNullable(at).map((AttackType a) -> {
				a.setAttackTypeId(attackTypeId);
				return attackTypeRepository.saveAndFlush(a);
			}).orElse(null);
		}).map(this::defineModelWithLinks).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("{attackTypeId:[0-9]+}")
	public ResponseEntity<Object> delete(@PathVariable("attackTypeId") Long attackTypeId) {
		return attackTypeRepository.findById(attackTypeId).map((AttackType atk) -> {
			attackTypeRepository.delete(atk);
			return ResponseEntity.noContent().build();
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
