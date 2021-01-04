package co.com.gsdd.dw2.converter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import co.com.gsdd.dw2.model.hateoas.LevelModel;
import co.com.gsdd.dw2.persistence.entities.Level;

@Component
public class LevelConverter implements GenericConverter<Level, LevelModel> {

	@Override
	public LevelModel convertToDomain(Level entity) {
		return Optional.ofNullable(entity)
				.map(e -> LevelModel.builder().levelId(e.getLevelId()).name(e.getName()).build())
				.orElse(null);
	}

	@Override
	public Level convertToEntity(LevelModel model) {
		return Optional.ofNullable(model)
				.map(m -> Level.builder().levelId(m.getLevelId()).name(m.getName()).build())
				.orElse(null);
	}

}
