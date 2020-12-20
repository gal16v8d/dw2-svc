package co.com.gsdd.dw2.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.DigimonXAttackModel;
import co.com.gsdd.dw2.persistence.entities.DigimonXAttack;
import co.com.gsdd.dw2.repository.DigimonRepository;
import co.com.gsdd.dw2.repository.DigimonXAttackRepository;

@RefreshScope
@RestController
@RequestMapping("v1/")
public class DigimonXAttackController {

	private final DigimonRepository digimonRepository;
	private final DigimonXAttackRepository digimonXAttackRepository;
	private final GenericConverter<DigimonXAttack, DigimonXAttackModel> digimonXAttackConverter;

	@Autowired
	public DigimonXAttackController(GenericConverter<DigimonXAttack, DigimonXAttackModel> digimonXAttackConverter,
			DigimonRepository digimonRepository, DigimonXAttackRepository digimonXAttackRepository) {
		this.digimonXAttackConverter = digimonXAttackConverter;
		this.digimonRepository = digimonRepository;
		this.digimonXAttackRepository = digimonXAttackRepository;
	}

	private DigimonXAttackModel getFromData(Long digimonId, Long attackId) {
		return DigimonXAttackModel.builder().attackId(attackId).digimonId(digimonId).build();
	}

	private DigimonXAttackModel defineModelWithLinks(DigimonXAttack entity) {
		DigimonXAttackModel model = digimonXAttackConverter.convertToDomain(entity);
		Link link = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(AttackController.class).getById(model.getAttackId()))
				.withRel("attack");
		// TODO add digimon link here
		model.add(link);
		return model;
	}

	@GetMapping("digimons/{digimonId:[0-9]+}/attacks")
	public ResponseEntity<CollectionModel<DigimonXAttackModel>> getAllAtk(@PathVariable("digimonId") Long digimonId) {
		List<DigimonXAttackModel> digimonXAttacks = Optional.ofNullable(digimonId).map(digimonRepository::findById)
				.orElseGet(Optional::empty).map(digimonXAttackRepository::findByDigimon)
				.orElseGet(Collections::emptyList).stream().map(this::defineModelWithLinks)
				.collect(Collectors.toList());
		Link link = WebMvcLinkBuilder.linkTo(DigimonXAttackController.class).withSelfRel();
		CollectionModel<DigimonXAttackModel> result = CollectionModel.of(digimonXAttacks, link);
		return ResponseEntity.ok(result);
	}

	@PostMapping("digimons/{digimonId:[0-9]+}/attacks/{attackId:[0-9]+}")
	public ResponseEntity<DigimonXAttackModel> associate(@PathVariable("digimonId") Long digimonId,
			@PathVariable("attackId") Long attackId) {
		return Optional.ofNullable(getFromData(digimonId, attackId)).map(digimonXAttackConverter::convertToEntity)
				.map(digimonXAttackRepository::saveAndFlush).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@DeleteMapping("digimons/{digimonId:[0-9]+}/attacks/{attackId:[0-9]+}")
	public ResponseEntity<Object> deassociate(@PathVariable("digimonId") Long digimonId,
			@PathVariable("attackId") Long attackId) {
		DigimonXAttack dxa = digimonXAttackConverter
				.convertToEntity(DigimonXAttackModel.builder().attackId(attackId).digimonId(digimonId).build());
		return Optional.ofNullable(dxa).map(
				d -> digimonXAttackRepository.findByDigimonAndAttack(d.getId().getDigimon(), d.getId().getAttack()))
				.map((DigimonXAttack ndxa) -> {
					digimonXAttackRepository.delete(ndxa);
					return ResponseEntity.noContent().build();
				}).orElseGet(() -> ResponseEntity.notFound().build());

	}

}
