package com.gsdd.dw2.converter;

import com.gsdd.dw2.model.LevelModel;
import com.gsdd.dw2.persistence.entities.Level;
import java.util.Optional;
import org.springframework.stereotype.Component;

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

  @Override
  public Level mapToEntity(LevelModel model, Level oldEntity) {
    Level newEntity = Level.builder().levelId(oldEntity.getLevelId()).build();
    newEntity
        .setName(Optional.ofNullable(model).map(LevelModel::getName).orElseGet(oldEntity::getName));
    return newEntity;
  }
}
