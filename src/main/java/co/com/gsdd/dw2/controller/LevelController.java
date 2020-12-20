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
import co.com.gsdd.dw2.model.LevelModel;
import co.com.gsdd.dw2.persistence.entities.Level;
import co.com.gsdd.dw2.repository.LevelRepository;

@RefreshScope
@RestController
@RequestMapping("v1/levels")
public class LevelController {

	private final LevelRepository levelRepository;
	private final GenericConverter<Level, LevelModel> levelConverter;

	@Autowired
	public LevelController(GenericConverter<Level, LevelModel> levelConverter, LevelRepository levelRepository) {
		this.levelConverter = levelConverter;
		this.levelRepository = levelRepository;
	}

	private LevelModel defineModelWithLinks(Level entity) {
		LevelModel model = levelConverter.convertToDomain(entity);
		Link link = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(LevelController.class).getById(model.getLevelId())).withSelfRel();
		model.add(link);
		return model;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<LevelModel>> getAll() {
		List<LevelModel> levels = levelRepository.findAll(Sort.by("levelId")).stream().map(this::defineModelWithLinks)
				.collect(Collectors.toList());
		Link link = WebMvcLinkBuilder.linkTo(LevelController.class).withSelfRel();
		CollectionModel<LevelModel> result = CollectionModel.of(levels, link);
		return ResponseEntity.ok(result);
	}

	@GetMapping("{levelId:[0-9]+}")
	public ResponseEntity<LevelModel> getById(@PathVariable("levelId") Long levelId) {
		return levelRepository.findById(levelId).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<LevelModel> save(@Valid @RequestBody LevelModel model) {
		return Optional.ofNullable(model).map(levelConverter::convertToEntity).map(levelRepository::saveAndFlush)
				.map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@PutMapping("{levelId:[0-9]+}")
	public ResponseEntity<LevelModel> update(@PathVariable("levelId") Long levelId,
			@Valid @RequestBody LevelModel model) {
		return levelRepository.findById(levelId).map((Level dbEntity) -> {
			Level lvl = levelConverter.convertToEntity(model);
			return Optional.ofNullable(lvl).map((Level l) -> {
				l.setLevelId(dbEntity.getLevelId());
				return levelRepository.saveAndFlush(l);
			}).orElse(null);
		}).map(this::defineModelWithLinks).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("{levelId:[0-9]+}")
	public ResponseEntity<Object> delete(@PathVariable("levelId") Long levelId) {
		return levelRepository.findById(levelId).map((Level lvl) -> {
			levelRepository.delete(lvl);
			return ResponseEntity.noContent().build();
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
