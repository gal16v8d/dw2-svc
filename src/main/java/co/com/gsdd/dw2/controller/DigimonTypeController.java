package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.model.DigimonTypeModel;
import co.com.gsdd.dw2.persistence.entities.DigimonType;
import co.com.gsdd.dw2.service.AbstractService;
import co.com.gsdd.dw2.service.DigimonTypeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api("Digimon Type CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/digimonTypes")
public class DigimonTypeController extends AbstractController<DigimonType, DigimonTypeModel> {

	private final DigimonTypeService digimonTypeService;

	@Override
	public Long getId(DigimonTypeModel model) {
		return model.getDigimonTypeId();
	}

	@Override
	public AbstractService<DigimonType, DigimonTypeModel> getService() {
		return digimonTypeService;
	}

}
