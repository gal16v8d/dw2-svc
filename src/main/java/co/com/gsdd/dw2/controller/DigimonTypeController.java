package co.com.gsdd.dw2.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
public class DigimonTypeController extends AbstractController<DigimonType, DigimonTypeModel> {

	private final DigimonTypeRepository digimonTypeRepository;
	private final GenericConverter<DigimonType, DigimonTypeModel> digimonTypeConverter;

	@Autowired
	public DigimonTypeController(GenericConverter<DigimonType, DigimonTypeModel> digimonTypeConverter,
			DigimonTypeRepository digimonTypeRepository) {
		this.digimonTypeConverter = digimonTypeConverter;
		this.digimonTypeRepository = digimonTypeRepository;
	}
	
	@Override
	public String getSortArg() {
		return "digimonTypeId";
	}

	@Override
	public Long getId(DigimonType entity) {
		return entity.getDigimonTypeId();
	}

	@Override
	public JpaRepository<DigimonType, Long> getRepo() {
		return digimonTypeRepository;
	}

	@Override
	public GenericConverter<DigimonType, DigimonTypeModel> getConverter() {
		return digimonTypeConverter;
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

}
