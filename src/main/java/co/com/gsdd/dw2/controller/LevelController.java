package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.hateoas.LevelModel;
import co.com.gsdd.dw2.persistence.entities.Level;
import co.com.gsdd.dw2.repository.LevelRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/levels")
public class LevelController extends AbstractController<Level, LevelModel> {

	private final LevelRepository levelRepository;
	private final GenericConverter<Level, LevelModel> levelConverter;

	@Override
	public String getSortArg() {
		return "levelId";
	}
	
	@Override
	public Long getId(Level entity) {
		return entity.getLevelId();
	}
	
	@Override
	public Level replaceId(Level entityNew, Level entityOrig) {
		entityNew.setLevelId(entityOrig.getLevelId());
		return entityNew;
	}
	
	@Override
	public JpaRepository<Level, Long> getRepo() {
		return levelRepository;
	}

	@Override
	public GenericConverter<Level, LevelModel> getConverter() {
		return levelConverter;
	}

}
