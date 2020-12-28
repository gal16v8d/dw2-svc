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
import co.com.gsdd.dw2.model.DigimonModel;
import co.com.gsdd.dw2.persistence.entities.Digimon;
import co.com.gsdd.dw2.repository.DigimonRepository;

@RefreshScope
@RestController
@RequestMapping("v1/digimons")
public class DigimonController extends AbstractController<Digimon, DigimonModel> {

	private final DigimonRepository digimonRepository;
	private final GenericConverter<Digimon, DigimonModel> digimonConverter;

	@Autowired
	public DigimonController(GenericConverter<Digimon, DigimonModel> digimonConverter,
			DigimonRepository digimonRepository) {
		this.digimonConverter = digimonConverter;
		this.digimonRepository = digimonRepository;
	}

	@Override
	public String getSortArg() {
		return "digimonId";
	}

	@Override
	public Long getId(Digimon entity) {
		return entity.getDigimonId();
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
		model.add(levelLink, typeLink);
		return model;
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

}
