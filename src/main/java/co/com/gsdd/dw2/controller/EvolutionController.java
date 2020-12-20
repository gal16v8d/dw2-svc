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
import co.com.gsdd.dw2.model.EvolutionModel;
import co.com.gsdd.dw2.persistence.entities.Evolution;
import co.com.gsdd.dw2.repository.EvolutionRepository;

@RefreshScope
@RestController
@RequestMapping("v1/evolutions")
public class EvolutionController {

	private final EvolutionRepository evolutionRepository;
	private final GenericConverter<Evolution, EvolutionModel> evolutionConverter;

	@Autowired
	public EvolutionController(GenericConverter<Evolution, EvolutionModel> evolutionConverter,
			EvolutionRepository evolutionRepository) {
		this.evolutionConverter = evolutionConverter;
		this.evolutionRepository = evolutionRepository;
	}

	private EvolutionModel defineModelWithLinks(Evolution entity) {
		EvolutionModel model = evolutionConverter.convertToDomain(entity);
		Link link = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(EvolutionController.class).getById(model.getEvolutionId()))
				.withSelfRel();
		model.add(link);
		// TODO add digimon links
		return model;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<EvolutionModel>> getAll() {
		List<EvolutionModel> levels = evolutionRepository.findAll(Sort.by("evolutionId")).stream()
				.map(this::defineModelWithLinks).collect(Collectors.toList());
		Link link = WebMvcLinkBuilder.linkTo(EvolutionController.class).withSelfRel();
		CollectionModel<EvolutionModel> result = CollectionModel.of(levels, link);
		return ResponseEntity.ok(result);
	}

	@GetMapping("{evolutionId:[0-9]+}")
	public ResponseEntity<EvolutionModel> getById(@PathVariable("evolutionId") Long evolutionId) {
		return evolutionRepository.findById(evolutionId).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<EvolutionModel> save(@Valid @RequestBody EvolutionModel model) {
		return Optional.ofNullable(model).map(evolutionConverter::convertToEntity)
				.map(evolutionRepository::saveAndFlush).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PutMapping("{evolutionId:[0-9]+}")
	public ResponseEntity<EvolutionModel> update(@PathVariable("evolutionId") Long evolutionId,
			@Valid @RequestBody EvolutionModel model) {
		return evolutionRepository.findById(evolutionId).map((Evolution dbEntity) -> {
			Evolution ev = evolutionConverter.convertToEntity(model);
			return Optional.ofNullable(ev).map((Evolution e) -> {
				e.setEvolutionId(dbEntity.getEvolutionId());
				return evolutionRepository.saveAndFlush(e);
			}).orElse(null);
		}).map(this::defineModelWithLinks).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("{evolutionId:[0-9]+}")
	public ResponseEntity<Object> delete(@PathVariable("evolutionId") Long evolutionId) {
		return evolutionRepository.findById(evolutionId).map((Evolution ev) -> {
			evolutionRepository.delete(ev);
			return ResponseEntity.noContent().build();
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
