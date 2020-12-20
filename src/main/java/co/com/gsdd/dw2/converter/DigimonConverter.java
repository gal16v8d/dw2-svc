package co.com.gsdd.dw2.converter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.gsdd.dw2.model.DigimonModel;
import co.com.gsdd.dw2.persistence.entities.Digimon;
import co.com.gsdd.dw2.persistence.entities.DigimonType;
import co.com.gsdd.dw2.persistence.entities.Level;
import co.com.gsdd.dw2.repository.DigimonTypeRepository;
import co.com.gsdd.dw2.repository.LevelRepository;

@Component
public class DigimonConverter implements GenericConverter<Digimon, DigimonModel> {

	private final DigimonTypeRepository digimonTypeRepository;
	private final LevelRepository levelRepository;

	@Autowired
	public DigimonConverter(DigimonTypeRepository digimonTypeRepository, LevelRepository levelRepository) {
		this.digimonTypeRepository = digimonTypeRepository;
		this.levelRepository = levelRepository;
	}

	@Override
	public DigimonModel convertToDomain(Digimon entity) {
		return Optional.ofNullable(entity)
				.map(e -> DigimonModel.builder().digimonId(e.getDigimonId())
						.digimonTypeId(e.getDigimonType().getDigimonTypeId()).levelId(e.getLevel().getLevelId())
						.name(e.getName()).build())
				.orElse(null);
	}

	@Override
	public Digimon convertToEntity(DigimonModel model) {
		Optional<DigimonType> typeOp = Optional.ofNullable(model).map(DigimonModel::getDigimonTypeId)
				.map(digimonTypeRepository::findById).orElseGet(Optional::empty);
		Optional<Level> levelOp = Optional.ofNullable(model).map(DigimonModel::getLevelId)
				.map(levelRepository::findById).orElseGet(Optional::empty);
		if (typeOp.isPresent() && levelOp.isPresent()) {
			return Optional.ofNullable(model).map(
					m -> Digimon.builder().name(model.getName()).level(levelOp.get()).digimonType(typeOp.get()).build())
					.orElse(null);
		}
		return null;

	}

}
