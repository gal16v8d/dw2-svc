package co.com.gsdd.dw2.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import co.com.gsdd.dw2.converter.GenericConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractController<T, D extends RepresentationModel<D>> {

	public abstract String getSortArg();

	public abstract Long getId(T entity);

	public abstract JpaRepository<T, Long> getRepo();

	public abstract GenericConverter<T, D> getConverter();

	public D defineModelWithLinks(T entity) {
		D model = getConverter().convertToDomain(entity);
		Link selfLink = generateSelfLink(getId(entity));
		if (selfLink != null) {
			model.add(selfLink);
		}
		return model;
	}

	public Link generateSelfLink(Long id) {
		try {
			Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getById(id))
					.withSelfRel();
			return selfLink;
		} catch (Exception e) {
			log.error("could not add self link", e);
		}
		return null;
	}

	@GetMapping
	public ResponseEntity<CollectionModel<D>> getAll() {
		List<D> models = getRepo().findAll(Sort.by(getSortArg())).stream().map(this::defineModelWithLinks)
				.collect(Collectors.toList());
		Link link = WebMvcLinkBuilder.linkTo(LevelController.class).withSelfRel();
		CollectionModel<D> result = CollectionModel.of(models, link);
		return ResponseEntity.ok(result);
	}

	@GetMapping("{id:[0-9]+}")
	public ResponseEntity<D> getById(@PathVariable("id") Long id) {
		return getRepo().findById(id).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<D> save(@Valid @RequestBody D model) {
		return Optional.ofNullable(model).map(getConverter()::convertToEntity).map(getRepo()::saveAndFlush)
				.map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@DeleteMapping("{id:[0-9]+}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		return getRepo().findById(id).map((T entity) -> {
			getRepo().delete(entity);
			return ResponseEntity.noContent().build();
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
