package com.gsdd.dw2.converter;

import com.gsdd.dw2.model.EvolutionModel;
import com.gsdd.dw2.persistence.entities.Digimon;
import com.gsdd.dw2.persistence.entities.Evolution;
import com.gsdd.dw2.repository.DigimonRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EvolutionConverter implements GenericConverter<Evolution, EvolutionModel> {

    private final DigimonRepository digimonRepository;

    @Override
    public EvolutionModel convertToDomain(Evolution entity) {
        return Optional.ofNullable(entity)
                .map(
                        e ->
                                EvolutionModel.builder()
                                        .baseDigimonId(e.getBaseDigimon().getDigimonId())
                                        .evolvedDigimonId(e.getEvolvedDigimon().getDigimonId())
                                        .dnaTimes(e.getDnaTimes())
                                        .evolutionId(entity.getEvolutionId())
                                        .build())
                .orElse(null);
    }

    @Override
    public Evolution convertToEntity(EvolutionModel model) {
        Optional<Digimon> baseOp =
                Optional.ofNullable(model)
                        .map(EvolutionModel::getBaseDigimonId)
                        .map(digimonRepository::findById)
                        .orElseGet(Optional::empty);
        Optional<Digimon> evolvedOp =
                Optional.ofNullable(model)
                        .map(EvolutionModel::getEvolvedDigimonId)
                        .map(digimonRepository::findById)
                        .orElseGet(Optional::empty);
        if (baseOp.isPresent() && evolvedOp.isPresent()) {
            return Optional.ofNullable(model)
                    .map(
                            m ->
                                    Evolution.builder()
                                            .dnaTimes(m.getDnaTimes())
                                            .baseDigimon(baseOp.get())
                                            .evolvedDigimon(evolvedOp.get())
                                            .build())
                    .orElse(null);
        }
        return null;
    }

    @Override
    public Evolution mapToEntity(EvolutionModel model, Evolution oldEntity) {
        Evolution newEntity = Evolution.builder().evolutionId(oldEntity.getEvolutionId()).build();
        Optional<EvolutionModel> modelOp = Optional.ofNullable(model);
        Optional<Digimon> baseOp =
                modelOp.map(EvolutionModel::getBaseDigimonId)
                        .map(digimonRepository::findById)
                        .orElseGet(Optional::empty);
        Optional<Digimon> evolvedOp =
                modelOp.map(EvolutionModel::getEvolvedDigimonId)
                        .map(digimonRepository::findById)
                        .orElseGet(Optional::empty);
        newEntity.setBaseDigimon(baseOp.orElseGet(oldEntity::getBaseDigimon));
        newEntity.setEvolvedDigimon(evolvedOp.orElseGet(oldEntity::getEvolvedDigimon));
        newEntity.setDnaTimes(
                modelOp.map(EvolutionModel::getDnaTimes).orElseGet(oldEntity::getDnaTimes));
        return newEntity;
    }
}
