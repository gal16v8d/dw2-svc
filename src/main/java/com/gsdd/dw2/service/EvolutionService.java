package com.gsdd.dw2.service;

import com.gsdd.dw2.converter.GenericConverter;
import com.gsdd.dw2.model.EvolutionModel;
import com.gsdd.dw2.persistence.entities.Evolution;
import com.gsdd.dw2.repository.EvolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EvolutionService implements BaseService<Evolution, EvolutionModel> {

  private final EvolutionRepository evolutionRepository;
  private final GenericConverter<Evolution, EvolutionModel> evolutionConverter;

  @Override
  public String getSortArg() {
    return "evolutionId";
  }

  @Override
  public Evolution replaceId(Evolution entityNew, Evolution entityOrig) {
    entityNew.setEvolutionId(entityOrig.getEvolutionId());
    return entityNew;
  }

  @Override
  public JpaRepository<Evolution, Long> getRepo() {
    return evolutionRepository;
  }

  @Override
  public GenericConverter<Evolution, EvolutionModel> getConverter() {
    return evolutionConverter;
  }
}
