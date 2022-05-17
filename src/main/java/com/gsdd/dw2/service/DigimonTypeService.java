package com.gsdd.dw2.service;

import com.gsdd.dw2.converter.GenericConverter;
import com.gsdd.dw2.model.DigimonTypeModel;
import com.gsdd.dw2.persistence.entities.DigimonType;
import com.gsdd.dw2.repository.DigimonTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DigimonTypeService extends AbstractService<DigimonType, DigimonTypeModel> {

  private final DigimonTypeRepository digimonTypeRepository;
  private final GenericConverter<DigimonType, DigimonTypeModel> digimonTypeConverter;

  @Override
  public String getSortArg() {
    return "digimonTypeId";
  }

  @Override
  public DigimonType replaceId(DigimonType entityNew, DigimonType entityOrig) {
    entityNew.setDigimonTypeId(entityOrig.getDigimonTypeId());
    return entityNew;
  }

  @Override
  public JpaRepository<DigimonType, Long> getRepo() {
    return digimonTypeRepository;
  }

  @Override
  public GenericConverter<DigimonType, DigimonTypeModel> getConverter() {
    return digimonTypeConverter;
  }
}
