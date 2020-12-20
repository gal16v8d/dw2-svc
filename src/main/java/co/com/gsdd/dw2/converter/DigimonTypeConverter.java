package co.com.gsdd.dw2.converter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import co.com.gsdd.dw2.model.DigimonTypeModel;
import co.com.gsdd.dw2.persistence.entities.DigimonType;

@Component
public class DigimonTypeConverter implements GenericConverter<DigimonType, DigimonTypeModel> {

	@Override
	public DigimonTypeModel convertToDomain(DigimonType entity) {
		return Optional.ofNullable(entity)
				.map(e -> DigimonTypeModel.builder().digimonTypeId(e.getDigimonTypeId()).name(e.getName()).build())
				.orElse(null);
	}

	@Override
	public DigimonType convertToEntity(DigimonTypeModel model) {
		return Optional.ofNullable(model)
				.map(m -> DigimonType.builder().digimonTypeId(m.getDigimonTypeId()).name(m.getName()).build())
				.orElse(null);
	}

}
