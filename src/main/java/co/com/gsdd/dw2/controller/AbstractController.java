package co.com.gsdd.dw2.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import co.com.gsdd.dw2.service.AbstractService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractController<T, D extends RepresentationModel<D>> {

	public abstract Long getId(D model);

	public abstract AbstractService<T, D> getService();

	public D defineModelWithLinks(D model) {
		Link selfLink = generateSelfLink(getId(model));
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

	@ApiOperation(value = "Allows to retrieve all", responseContainer = "List")
	@GetMapping
	public ResponseEntity<CollectionModel<D>> getAll() {
		List<D> models = getService().getAll().stream().map(this::defineModelWithLinks).collect(Collectors.toList());
		Link link = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();
		CollectionModel<D> result = CollectionModel.of(models, link);
		return ResponseEntity.ok(result);
	}

	@ApiOperation(value = "Retrieve a single record by id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Matching data"),
			@ApiResponse(code = 404, message = "Can not find any data by given id") })
	@GetMapping("{id:[0-9]+}")
	public ResponseEntity<D> getById(@PathVariable("id") Long id) {
		return Optional.ofNullable(getService().getById(id)).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Store given data")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Save success"),
			@ApiResponse(code = 400, message = "If some missing data or wrong payload") })
	@PostMapping
	public ResponseEntity<D> save(@Valid @RequestBody D model) {
		return Optional.ofNullable(getService().save(model)).map(this::defineModelWithLinks).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.badRequest().build());
	}

	@ApiOperation(value = "Fully updates matching data")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Update success"),
			@ApiResponse(code = 400, message = "If some missing data or wrong payload"),
			@ApiResponse(code = 404, message = "Can not find any data by given id") })
	@PutMapping("{id:[0-9]+}")
	public ResponseEntity<D> update(@PathVariable("id") Long id, @Valid @RequestBody D model) {
		return Optional.ofNullable(getService().update(id, model)).map(this::defineModelWithLinks)
				.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Partial update matching data")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Update success"),
			@ApiResponse(code = 400, message = "If some missing data or wrong payload"),
			@ApiResponse(code = 404, message = "Can not find any data by given id") })
	@PatchMapping("{id:[0-9]+}")
	public ResponseEntity<D> patch(@PathVariable("id") Long id, @RequestBody D model) {
		return Optional.ofNullable(getService().patch(id, model)).map(this::defineModelWithLinks)
				.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Delete matching data")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Delete success"),
			@ApiResponse(code = 404, message = "Can not find any data by given id") })
	@DeleteMapping("{id:[0-9]+}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		return Optional.ofNullable(getService().delete(id)).map(result -> ResponseEntity.noContent().build())
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
}
