package co.com.gsdd.dw2.converter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import co.com.gsdd.dw2.model.AttackTargetTypeModel;
import co.com.gsdd.dw2.persistence.entities.AttackTargetType;

@Component
public class AttackTargetTypeConverter implements GenericConverter<AttackTargetType, AttackTargetTypeModel> {

	@Override
	public AttackTargetTypeModel convertToDomain(AttackTargetType entity) {
		return Optional.ofNullable(entity).map(e -> AttackTargetTypeModel.builder()
				.attackTargetTypeId(e.getAttackTargetTypeId()).name(e.getName()).build()).orElse(null);
	}

	@Override
	public AttackTargetType convertToEntity(AttackTargetTypeModel model) {
		return Optional.ofNullable(model).map(
				m -> AttackTargetType.builder().attackTargetTypeId(m.getAttackTargetTypeId()).name(m.getName()).build())
				.orElse(null);
	}

	@Override
	public AttackTargetType mapToEntity(AttackTargetTypeModel model, AttackTargetType oldEntity) {
		AttackTargetType newEntity = AttackTargetType.builder().attackTargetTypeId(oldEntity.getAttackTargetTypeId())
				.build();
		newEntity.setName(Optional.ofNullable(model).map(AttackTargetTypeModel::getName).orElseGet(oldEntity::getName));
		return newEntity;
	}

}
