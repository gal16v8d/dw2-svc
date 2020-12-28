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
import co.com.gsdd.dw2.model.LevelModel;
import co.com.gsdd.dw2.persistence.entities.Level;
import co.com.gsdd.dw2.repository.LevelRepository;

@RefreshScope
@RestController
@RequestMapping("v1/levels")
public class LevelController extends AbstractController<Level, LevelModel> {

	private final LevelRepository levelRepository;
	private final GenericConverter<Level, LevelModel> levelConverter;

	@Autowired
	public LevelController(GenericConverter<Level, LevelModel> levelConverter, LevelRepository levelRepository) {
		this.levelConverter = levelConverter;
		this.levelRepository = levelRepository;
	}

	@Override
	public String getSortArg() {
		return "levelId";
	}
	
	@Override
	public Long getId(Level entity) {
		return entity.getLevelId();
	}
	
	@Override
	public JpaRepository<Level, Long> getRepo() {
		return levelRepository;
	}

	@Override
	public GenericConverter<Level, LevelModel> getConverter() {
		return levelConverter;
	}

	@PutMapping("{levelId:[0-9]+}")
	public ResponseEntity<LevelModel> update(@PathVariable("levelId") Long levelId,
			@Valid @RequestBody LevelModel model) {
		return getRepo().findById(levelId).map((Level dbEntity) -> {
			Level lvl = getConverter().convertToEntity(model);
			return Optional.ofNullable(lvl).map((Level l) -> {
				l.setLevelId(dbEntity.getLevelId());
				return getRepo().saveAndFlush(l);
			}).orElse(null);
		}).map(this::defineModelWithLinks).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
