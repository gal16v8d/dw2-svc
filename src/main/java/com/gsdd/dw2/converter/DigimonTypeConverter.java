package com.gsdd.dw2.converter;

import com.gsdd.dw2.model.DigimonTypeModel;
import com.gsdd.dw2.persistence.entities.DigimonType;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class DigimonTypeConverter implements GenericConverter<DigimonType, DigimonTypeModel> {

  @Override
  public DigimonTypeModel convertToDomain(DigimonType entity) {
    return Optional.ofNullable(entity)
        .map(
            e ->
                DigimonTypeModel.builder()
                    .digimonTypeId(e.getDigimonTypeId())
                    .name(e.getName())
                    .build())
        .orElse(null);
  }

  @Override
  public DigimonType convertToEntity(DigimonTypeModel model) {
    return Optional.ofNullable(model)
        .map(
            m ->
                DigimonType.builder().digimonTypeId(m.getDigimonTypeId()).name(m.getName()).build())
        .orElse(null);
  }

  @Override
  public DigimonType mapToEntity(DigimonTypeModel model, DigimonType oldEntity) {
    DigimonType newEntity =
        DigimonType.builder().digimonTypeId(oldEntity.getDigimonTypeId()).build();
    newEntity.setName(
        Optional.ofNullable(model).map(DigimonTypeModel::getName).orElseGet(oldEntity::getName));
    return newEntity;
  }
}
