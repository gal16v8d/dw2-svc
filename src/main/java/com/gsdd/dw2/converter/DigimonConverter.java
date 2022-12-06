package com.gsdd.dw2.converter;

import com.gsdd.dw2.model.DigimonModel;
import com.gsdd.dw2.persistence.entities.Digimon;
import com.gsdd.dw2.persistence.entities.DigimonType;
import com.gsdd.dw2.persistence.entities.Element;
import com.gsdd.dw2.persistence.entities.Level;
import com.gsdd.dw2.repository.DigimonTypeRepository;
import com.gsdd.dw2.repository.ElementRepository;
import com.gsdd.dw2.repository.LevelRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DigimonConverter implements GenericConverter<Digimon, DigimonModel> {

  private final DigimonTypeRepository digimonTypeRepository;
  private final LevelRepository levelRepository;
  private final ElementRepository elementRepository;

  @Override
  public DigimonModel convertToDomain(Digimon entity) {
    return Optional.ofNullable(entity)
        .map(
            e -> DigimonModel.builder()
                .digimonId(e.getDigimonId())
                .digimonTypeId(e.getDigimonType().getDigimonTypeId())
                .levelId(e.getLevel().getLevelId())
                .elementId(e.getElement().getElementId())
                .name(e.getName())
                .build())
        .orElse(null);
  }

  @Override
  public Digimon convertToEntity(DigimonModel model) {
    Optional<DigimonType> typeOp = Optional.ofNullable(model)
        .map(DigimonModel::getDigimonTypeId)
        .map(digimonTypeRepository::findById)
        .orElseGet(Optional::empty);
    Optional<Level> levelOp = Optional.ofNullable(model)
        .map(DigimonModel::getLevelId)
        .map(levelRepository::findById)
        .orElseGet(Optional::empty);
    Optional<Element> elementOp = Optional.ofNullable(model)
        .map(DigimonModel::getElementId)
        .map(elementRepository::findById)
        .orElseGet(Optional::empty);
    if (typeOp.isPresent() && levelOp.isPresent() && elementOp.isPresent()) {
      return Optional.ofNullable(model)
          .map(
              m -> Digimon.builder()
                  .name(model.getName())
                  .level(levelOp.get())
                  .digimonType(typeOp.get())
                  .element(elementOp.get())
                  .build())
          .orElse(null);
    }
    return null;
  }

  @Override
  public Digimon mapToEntity(DigimonModel model, Digimon oldEntity) {
    Digimon newEntity = Digimon.builder().digimonId(oldEntity.getDigimonId()).build();
    Optional<DigimonModel> modelOp = Optional.ofNullable(model);
    Optional<DigimonType> typeOp = modelOp.map(DigimonModel::getDigimonTypeId)
        .map(digimonTypeRepository::findById)
        .orElseGet(Optional::empty);
    Optional<Level> levelOp = modelOp.map(DigimonModel::getLevelId)
        .map(levelRepository::findById)
        .orElseGet(Optional::empty);
    Optional<Element> elementOp = modelOp.map(DigimonModel::getElementId)
        .map(elementRepository::findById)
        .orElseGet(Optional::empty);
    newEntity.setName(modelOp.map(DigimonModel::getName).orElseGet(oldEntity::getName));
    newEntity.setDigimonType(typeOp.orElseGet(oldEntity::getDigimonType));
    newEntity.setLevel(levelOp.orElseGet(oldEntity::getLevel));
    newEntity.setElement(elementOp.orElseGet(oldEntity::getElement));
    return newEntity;
  }
}
