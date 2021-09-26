package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.model.LevelModel;
import co.com.gsdd.dw2.persistence.entities.Level;
import co.com.gsdd.dw2.service.AbstractService;
import co.com.gsdd.dw2.service.LevelService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api("Level CRUD operations")
@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/levels")
public class LevelController extends AbstractController<Level, LevelModel> {

	private final LevelService levelService;
	
	@Override
	public Long getId(LevelModel model) {
		return model.getLevelId();
	}

	@Override
	public AbstractService<Level, LevelModel> getService() {
		return levelService;
	}

}
