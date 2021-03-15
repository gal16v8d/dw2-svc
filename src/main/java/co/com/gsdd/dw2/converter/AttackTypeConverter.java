package co.com.gsdd.dw2.converter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import co.com.gsdd.dw2.model.hateoas.AttackTypeModel;
import co.com.gsdd.dw2.persistence.entities.AttackType;

@Component
public class AttackTypeConverter implements GenericConverter<AttackType, AttackTypeModel> {

	@Override
	public AttackTypeModel convertToDomain(AttackType entity) {
		return Optional.ofNullable(entity)
				.map(e -> AttackTypeModel.builder().attackTypeId(e.getAttackTypeId()).name(e.getName()).build())
				.orElse(null);
	}

	@Override
	public AttackType convertToEntity(AttackTypeModel model) {
		return Optional.ofNullable(model)
				.map(m -> AttackType.builder().attackTypeId(m.getAttackTypeId()).name(m.getName()).build())
				.orElse(null);
	}

	@Override
	public AttackType mapToEntity(AttackTypeModel model, AttackType oldEntity) {
		AttackType newEntity = AttackType.builder().attackTypeId(oldEntity.getAttackTypeId()).build();
		newEntity.setName(Optional.ofNullable(model).map(AttackTypeModel::getName).orElseGet(oldEntity::getName));
		return newEntity;
	}

}
