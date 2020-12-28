package co.com.gsdd.dw2.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
public class EvolutionController extends AbstractController<Evolution, EvolutionModel> {

	private final EvolutionRepository evolutionRepository;
	private final GenericConverter<Evolution, EvolutionModel> evolutionConverter;

	@Autowired
	public EvolutionController(GenericConverter<Evolution, EvolutionModel> evolutionConverter,
			EvolutionRepository evolutionRepository) {
		this.evolutionConverter = evolutionConverter;
		this.evolutionRepository = evolutionRepository;
	}
	
	@Override
	public String getSortArg() {
		return "evolutionId";
	}
	
	@Override
	public Long getId(Evolution entity) {
		return entity.getEvolutionId();
	}

	@Override
	public JpaRepository<Evolution, Long> getRepo() {
		return evolutionRepository;
	}

	@Override
	public GenericConverter<Evolution, EvolutionModel> getConverter() {
		return evolutionConverter;
	}

	@Override
	public EvolutionModel defineModelWithLinks(Evolution entity) {
		EvolutionModel model = super.defineModelWithLinks(entity);
		Link baseLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(DigimonController.class).getById(model.getBaseDigimonId()))
				.withRel("baseDigimon");
		Link evolvedLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(DigimonController.class).getById(model.getEvolvedDigimonId()))
				.withRel("evolvedDigimon");
		model.add(baseLink, evolvedLink);
		return model;
	}

	@PutMapping("{evolutionId:[0-9]+}")
	public ResponseEntity<EvolutionModel> update(@PathVariable("evolutionId") Long evolutionId,
			@Valid @RequestBody EvolutionModel model) {
		return getRepo().findById(evolutionId).map((Evolution dbEntity) -> {
			Evolution ev = getConverter().convertToEntity(model);
			return Optional.ofNullable(ev).map((Evolution e) -> {
				e.setEvolutionId(dbEntity.getEvolutionId());
				return getRepo().saveAndFlush(e);
			}).orElse(null);
		}).map(this::defineModelWithLinks).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
