package co.com.gsdd.dw2.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.EvolutionModel;
import co.com.gsdd.dw2.persistence.entities.Evolution;
import co.com.gsdd.dw2.repository.EvolutionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EvolutionService extends AbstractService<Evolution, EvolutionModel> {

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
