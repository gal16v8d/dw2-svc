package co.com.gsdd.dw2.converter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import co.com.gsdd.dw2.model.EvolutionModel;
import co.com.gsdd.dw2.persistence.entities.Digimon;
import co.com.gsdd.dw2.persistence.entities.Evolution;
import co.com.gsdd.dw2.repository.DigimonRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class EvolutionConverter implements GenericConverter<Evolution, EvolutionModel> {

	private final DigimonRepository digimonRepository;
	
	@Override
	public EvolutionModel convertToDomain(Evolution entity) {
		return Optional.ofNullable(entity)
				.map(e -> EvolutionModel.builder().baseDigimonId(e.getBaseDigimon().getDigimonId())
						.evolvedDigimonId(e.getEvolvedDigimon().getDigimonId()).dnaTimes(e.getDnaTimes())
						.evolutionId(entity.getEvolutionId()).build())
				.orElse(null);
	}

	@Override
	public Evolution convertToEntity(EvolutionModel model) {
		Optional<Digimon> baseOp = Optional.ofNullable(model).map(EvolutionModel::getBaseDigimonId)
				.map(digimonRepository::findById).orElseGet(Optional::empty);
		Optional<Digimon> evolvedOp = Optional.ofNullable(model).map(EvolutionModel::getEvolvedDigimonId)
				.map(digimonRepository::findById).orElseGet(Optional::empty);
		if (baseOp.isPresent() && evolvedOp.isPresent()) {
			return Optional.ofNullable(model).map(m -> Evolution.builder().dnaTimes(m.getDnaTimes())
					.baseDigimon(baseOp.get()).evolvedDigimon(evolvedOp.get()).build()).orElse(null);
		}
		return null;
	}

}
