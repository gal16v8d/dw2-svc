package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.hateoas.DigimonModel;
import co.com.gsdd.dw2.persistence.entities.Digimon;
import co.com.gsdd.dw2.repository.DigimonRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/digimons")
public class DigimonController extends AbstractController<Digimon, DigimonModel> {

	private final DigimonRepository digimonRepository;
	private final GenericConverter<Digimon, DigimonModel> digimonConverter;

	@Override
	public String getSortArg() {
		return "digimonId";
	}

	@Override
	public Long getId(Digimon entity) {
		return entity.getDigimonId();
	}

	@Override
	public Digimon replaceId(Digimon entityNew, Digimon entityOrig) {
		entityNew.setDigimonId(entityOrig.getDigimonId());
		return entityNew;
	}

	@Override
	public JpaRepository<Digimon, Long> getRepo() {
		return digimonRepository;
	}

	@Override
	public GenericConverter<Digimon, DigimonModel> getConverter() {
		return digimonConverter;
	}

	@Override
	public DigimonModel defineModelWithLinks(Digimon entity) {
		DigimonModel model = super.defineModelWithLinks(entity);
		Link levelLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(LevelController.class).getById(model.getLevelId())).withRel("level");
		Link typeLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(DigimonTypeController.class).getById(model.getDigimonTypeId()))
				.withRel("type");
		Link elementLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(ElementController.class).getById(model.getElementId()))
				.withRel("element");
		model.add(levelLink, typeLink, elementLink);
		return model;
	}

}
