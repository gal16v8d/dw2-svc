package co.com.gsdd.dw2.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import co.com.gsdd.dw2.model.hateoas.DigimonXAttackModel;
import co.com.gsdd.dw2.service.DigimonXAttackService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/")
public class DigimonXAttackController {

	private final DigimonXAttackService digimonXAttackService;

	private DigimonXAttackModel defineModelWithLinks(DigimonXAttackModel model) {
		DigimonXAttackModel linkedModel = DigimonXAttackModel.builder().attackId(model.getAttackId())
				.digimonId(model.getDigimonId()).build();
		Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DigimonXAttackController.class)
				.getById(linkedModel.getDigimonId(), linkedModel.getAttackId())).withSelfRel();
		Link linkAttack = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(AttackController.class).getById(linkedModel.getAttackId()))
				.withRel("attack");
		Link linkDigimon = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(DigimonController.class).getById(linkedModel.getDigimonId()))
				.withRel("digimon");
		linkedModel.add(selfLink, linkAttack, linkDigimon);
		return linkedModel;
	}

	@GetMapping("digimons/{digimonId:[0-9]+}/attacks")
	public ResponseEntity<CollectionModel<DigimonXAttackModel>> getAllAtk(@PathVariable("digimonId") Long digimonId) {
		List<DigimonXAttackModel> digimonXAttacks = digimonXAttackService.getAllAtk(digimonId).stream()
				.map(this::defineModelWithLinks).collect(Collectors.toList());
		Link link = WebMvcLinkBuilder.linkTo(DigimonXAttackController.class).withSelfRel();
		CollectionModel<DigimonXAttackModel> result = CollectionModel.of(digimonXAttacks, link);
		return ResponseEntity.ok(result);
	}

	@GetMapping("digimons/{digimonId:[0-9]+}/attacks/{attackId:[0-9]+}")
	public ResponseEntity<DigimonXAttackModel> getById(@PathVariable("digimonId") Long digimonId,
			@PathVariable("attackId") Long attackId) {
		DigimonXAttackModel dxa = digimonXAttackService.getById(digimonId, attackId);
		return Optional.ofNullable(dxa).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PostMapping("digimons/{digimonId:[0-9]+}/attacks/{attackId:[0-9]+}")
	public ResponseEntity<DigimonXAttackModel> associate(@PathVariable("digimonId") Long digimonId,
			@PathVariable("attackId") Long attackId) {
		return Optional.ofNullable(digimonXAttackService.associate(digimonId, attackId)).map(this::defineModelWithLinks)
				.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@DeleteMapping("digimons/{digimonId:[0-9]+}/attacks/{attackId:[0-9]+}")
	public ResponseEntity<Object> deassociate(@PathVariable("digimonId") Long digimonId,
			@PathVariable("attackId") Long attackId) {
		return Optional.ofNullable(digimonXAttackService.deassociate(digimonId, attackId))
				.map(result -> ResponseEntity.noContent().build()).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
