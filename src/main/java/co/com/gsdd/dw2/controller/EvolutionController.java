package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.hateoas.EvolutionModel;
import co.com.gsdd.dw2.persistence.entities.Evolution;
import co.com.gsdd.dw2.repository.EvolutionRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/evolutions")
public class EvolutionController extends AbstractController<Evolution, EvolutionModel> {

	private final EvolutionRepository evolutionRepository;
	private final GenericConverter<Evolution, EvolutionModel> evolutionConverter;
	
	@Override
	public String getSortArg() {
		return "evolutionId";
	}
	
	@Override
	public Long getId(Evolution entity) {
		return entity.getEvolutionId();
	}
	
	@Override
	public Evolution replaceId(Evolution entityNew, Evolution entityOrig) {
		entityNew.setEvolutionId(entityOrig.getEvolutionId());
		return entityNew;
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

}
