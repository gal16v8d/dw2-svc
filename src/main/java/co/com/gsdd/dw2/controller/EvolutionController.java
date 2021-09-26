package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.model.EvolutionModel;
import co.com.gsdd.dw2.persistence.entities.Evolution;
import co.com.gsdd.dw2.service.AbstractService;
import co.com.gsdd.dw2.service.EvolutionService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api("Evolution CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/evolutions")
public class EvolutionController extends AbstractController<Evolution, EvolutionModel> {

	private final EvolutionService evolutionService;
	
	@Override
	public Long getId(EvolutionModel model) {
		return model.getEvolutionId();
	}

	@Override
	public AbstractService<Evolution, EvolutionModel> getService() {
		return evolutionService;
	}

}
