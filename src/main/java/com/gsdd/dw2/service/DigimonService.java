package com.gsdd.dw2.service;

import com.gsdd.dw2.converter.GenericConverter;
import com.gsdd.dw2.model.DigimonModel;
import com.gsdd.dw2.persistence.entities.Digimon;
import com.gsdd.dw2.repository.DigimonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DigimonService extends AbstractService<Digimon, DigimonModel> {

  private final DigimonRepository digimonRepository;
  private final GenericConverter<Digimon, DigimonModel> digimonConverter;

  @Override
  public String getSortArg() {
    return "digimonId";
  }

  @Override
  public Digimon replaceId(Digimon entityNew, Digimon entityOrig) {
    entityNew.setDigimonId(entityOrig.getDigimonId());
    return entityNew;
  }

  @Override
  public JpaRepository<Digimon, Long> getRepo() {
    return digimonRepository;
  }

  @Override
  public GenericConverter<Digimon, DigimonModel> getConverter() {
    return digimonConverter;
  }
}
