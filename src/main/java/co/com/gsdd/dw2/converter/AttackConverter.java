package co.com.gsdd.dw2.converter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import co.com.gsdd.dw2.model.hateoas.AttackModel;
import co.com.gsdd.dw2.persistence.entities.Attack;
import co.com.gsdd.dw2.persistence.entities.AttackType;
import co.com.gsdd.dw2.repository.AttackTypeRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class AttackConverter implements GenericConverter<Attack, AttackModel> {

	private final AttackTypeRepository attackTypeRepository;

	@Override
	public AttackModel convertToDomain(Attack entity) {
		return Optional.ofNullable(entity)
				.map(e -> AttackModel.builder().attackId(e.getAttackId())
						.attackTypeId(e.getAttackType().getAttackTypeId()).name(e.getName()).mp(e.getMp()).build())
				.orElse(null);
	}

	@Override
	public Attack convertToEntity(AttackModel model) {
		Optional<AttackType> typeOp = Optional.ofNullable(model).map(AttackModel::getAttackTypeId)
				.map(attackTypeRepository::findById).orElseGet(Optional::empty);
		return typeOp.map(t -> Optional.ofNullable(model).map(
				m -> Attack.builder().attackId(m.getAttackId()).attackType(t).name(m.getName()).mp(m.getMp()).build())
				.orElse(null)).orElse(null);
	}

	@Override
	public Attack mapToEntity(AttackModel model, Attack oldEntity) {
		Attack newEntity = Attack.builder().attackId(oldEntity.getAttackId()).build();
		Optional<AttackModel> modelOp = Optional.ofNullable(model);
		newEntity.setMp(modelOp.map(AttackModel::getMp).orElseGet(oldEntity::getMp));
		newEntity.setName(modelOp.map(AttackModel::getName).orElseGet(oldEntity::getName));
		Optional<AttackType> typeOp = modelOp.map(AttackModel::getAttackTypeId).map(attackTypeRepository::findById)
				.orElseGet(Optional::empty);
		newEntity.setAttackType(typeOp.orElseGet(oldEntity::getAttackType));
		return newEntity;
	}

}
