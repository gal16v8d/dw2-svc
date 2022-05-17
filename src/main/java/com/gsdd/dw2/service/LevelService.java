package com.gsdd.dw2.service;

import com.gsdd.dw2.converter.GenericConverter;
import com.gsdd.dw2.model.LevelModel;
import com.gsdd.dw2.persistence.entities.Level;
import com.gsdd.dw2.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LevelService extends AbstractService<Level, LevelModel> {

  private final LevelRepository levelRepository;
  private final GenericConverter<Level, LevelModel> levelConverter;

  @Override
  public String getSortArg() {
    return "levelId";
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
