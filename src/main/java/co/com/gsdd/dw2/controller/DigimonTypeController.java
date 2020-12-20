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
import co.com.gsdd.dw2.model.DigimonTypeModel;
import co.com.gsdd.dw2.persistence.entities.DigimonType;
import co.com.gsdd.dw2.repository.DigimonTypeRepository;

@RefreshScope
@RestController
@RequestMapping("v1/digimonTypes")
public class DigimonTypeController {

	private final DigimonTypeRepository digimonTypeRepository;
	private final GenericConverter<DigimonType, DigimonTypeModel> digimonTypeConverter;

	@Autowired
	public DigimonTypeController(GenericConverter<DigimonType, DigimonTypeModel> digimonTypeConverter,
			DigimonTypeRepository digimonTypeRepository) {
		this.digimonTypeConverter = digimonTypeConverter;
		this.digimonTypeRepository = digimonTypeRepository;
	}

	private DigimonTypeModel defineModelWithLinks(DigimonType entity) {
		DigimonTypeModel model = digimonTypeConverter.convertToDomain(entity);
		Link link = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(DigimonTypeController.class).getById(model.getDigimonTypeId()))
				.withSelfRel();
		model.add(link);
		return model;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<DigimonTypeModel>> getAll() {
		List<DigimonTypeModel> digimonTypes = digimonTypeRepository.findAll(Sort.by("digimonTypeId")).stream()
				.map(this::defineModelWithLinks).collect(Collectors.toList());
		Link link = WebMvcLinkBuilder.linkTo(DigimonTypeController.class).withSelfRel();
		CollectionModel<DigimonTypeModel> result = CollectionModel.of(digimonTypes, link);
		return ResponseEntity.ok(result);
	}

	@GetMapping("{digimonTypeId:[0-9]+}")
	public ResponseEntity<DigimonTypeModel> getById(@PathVariable("digimonTypeId") Long digimonTypeId) {
		return digimonTypeRepository.findById(digimonTypeId).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<DigimonTypeModel> save(@Valid @RequestBody DigimonTypeModel model) {
		return Optional.ofNullable(model).map(digimonTypeConverter::convertToEntity)
				.map(digimonTypeRepository::saveAndFlush).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PutMapping("{digimonTypeId:[0-9]+}")
	public ResponseEntity<DigimonTypeModel> update(@PathVariable("digimonTypeId") Long digimonTypeId,
			@Valid @RequestBody DigimonTypeModel model) {
		return digimonTypeRepository.findById(digimonTypeId).map((DigimonType dbEntity) -> {
			DigimonType at = digimonTypeConverter.convertToEntity(model);
			return Optional.ofNullable(at).map((DigimonType a) -> {
				a.setDigimonTypeId(digimonTypeId);
				return digimonTypeRepository.saveAndFlush(a);
			}).orElse(null);
		}).map(this::defineModelWithLinks).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("{digimonTypeId:[0-9]+}")
	public ResponseEntity<Object> delete(@PathVariable("digimonTypeId") Long digimonTypeId) {
		return digimonTypeRepository.findById(digimonTypeId).map((DigimonType atk) -> {
			digimonTypeRepository.delete(atk);
			return ResponseEntity.noContent().build();
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
