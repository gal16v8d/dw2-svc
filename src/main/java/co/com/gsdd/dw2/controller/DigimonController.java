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
import co.com.gsdd.dw2.model.DigimonModel;
import co.com.gsdd.dw2.persistence.entities.Digimon;
import co.com.gsdd.dw2.repository.DigimonRepository;

@RefreshScope
@RestController
@RequestMapping("v1/digimons")
public class DigimonController {

	private final DigimonRepository digimonRepository;
	private final GenericConverter<Digimon, DigimonModel> digimonConverter;

	@Autowired
	public DigimonController(GenericConverter<Digimon, DigimonModel> digimonConverter,
			DigimonRepository digimonRepository) {
		this.digimonConverter = digimonConverter;
		this.digimonRepository = digimonRepository;
	}

	private DigimonModel defineModelWithLinks(Digimon entity) {
		DigimonModel model = digimonConverter.convertToDomain(entity);
		Link link = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(DigimonController.class).getById(model.getDigimonId()))
				.withSelfRel();
		model.add(link);
		return model;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<DigimonModel>> getAll() {
		List<DigimonModel> models = digimonRepository.findAll(Sort.by("digimonId")).stream()
				.map(this::defineModelWithLinks).collect(Collectors.toList());
		Link link = WebMvcLinkBuilder.linkTo(DigimonController.class).withSelfRel();
		CollectionModel<DigimonModel> result = CollectionModel.of(models, link);
		return ResponseEntity.ok(result);
	}

	@GetMapping("{digimonId:[0-9]+}")
	public ResponseEntity<DigimonModel> getById(@PathVariable("digimonId") Long digimonId) {
		return digimonRepository.findById(digimonId).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<DigimonModel> save(@Valid @RequestBody DigimonModel model) {
		return Optional.ofNullable(model).map(digimonConverter::convertToEntity).map(digimonRepository::saveAndFlush)
				.map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PutMapping("{digimonId:[0-9]+}")
	public ResponseEntity<DigimonModel> update(@PathVariable("digimonId") Long digimonId,
			@Valid @RequestBody DigimonModel model) {
		return digimonRepository.findById(digimonId).map((Digimon dbEntity) -> {
			Digimon dig = digimonConverter.convertToEntity(model);
			return Optional.ofNullable(dig).map((Digimon d) -> {
				d.setDigimonId(dbEntity.getDigimonId());
				return digimonRepository.saveAndFlush(d);
			}).orElse(null);
		}).map(this::defineModelWithLinks).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("{digimonId:[0-9]+}")
	public ResponseEntity<Object> delete(@PathVariable("digimonId") Long digimonId) {
		return digimonRepository.findById(digimonId).map((Digimon dig) -> {
			digimonRepository.delete(dig);
			return ResponseEntity.noContent().build();
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
