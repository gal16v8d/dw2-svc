package co.com.gsdd.dw2.converter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import co.com.gsdd.dw2.model.hateoas.DigimonXAttackModel;
import co.com.gsdd.dw2.persistence.entities.Attack;
import co.com.gsdd.dw2.persistence.entities.Digimon;
import co.com.gsdd.dw2.persistence.entities.DigimonXAttack;
import co.com.gsdd.dw2.persistence.entities.DigimonXAttackPK;
import co.com.gsdd.dw2.repository.AttackRepository;
import co.com.gsdd.dw2.repository.DigimonRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DigimonXAttackConverter implements GenericConverter<DigimonXAttack, DigimonXAttackModel> {

	private final DigimonRepository digimonRepository;
	private final AttackRepository attackRepository;

	@Override
	public DigimonXAttackModel convertToDomain(DigimonXAttack entity) {
		return Optional.ofNullable(entity).map(e -> DigimonXAttackModel.builder()
				.attackId(e.getId().getAttack().getAttackId()).digimonId(e.getId().getDigimon().getDigimonId()).build())
				.orElse(null);
	}

	@Override
	public DigimonXAttack convertToEntity(DigimonXAttackModel model) {
		Optional<Digimon> digi = Optional.ofNullable(model).map(DigimonXAttackModel::getDigimonId)
				.map(digimonRepository::findById).orElseGet(Optional::empty);
		Optional<Attack> atk = Optional.ofNullable(model).map(DigimonXAttackModel::getAttackId)
				.map(attackRepository::findById).orElseGet(Optional::empty);
		if (digi.isPresent() && atk.isPresent()) {
			return DigimonXAttack.builder().id(new DigimonXAttackPK(digi.get(), atk.get())).build();
		}
		return null;
	}

}
