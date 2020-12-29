package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.DigimonTypeModel;
import co.com.gsdd.dw2.persistence.entities.DigimonType;
import co.com.gsdd.dw2.repository.DigimonTypeRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/digimonTypes")
public class DigimonTypeController extends AbstractController<DigimonType, DigimonTypeModel> {

	private final DigimonTypeRepository digimonTypeRepository;
	private final GenericConverter<DigimonType, DigimonTypeModel> digimonTypeConverter;
	
	@Override
	public String getSortArg() {
		return "digimonTypeId";
	}

	@Override
	public Long getId(DigimonType entity) {
		return entity.getDigimonTypeId();
	}
	
	@Override
	public DigimonType replaceId(DigimonType entityNew, DigimonType entityOrig) {
		entityNew.setDigimonTypeId(entityOrig.getDigimonTypeId());
		return entityNew;
	}

	@Override
	public JpaRepository<DigimonType, Long> getRepo() {
		return digimonTypeRepository;
	}

	@Override
	public GenericConverter<DigimonType, DigimonTypeModel> getConverter() {
		return digimonTypeConverter;
	}

}
