package co.com.gsdd.dw2.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.AttackTargetTypeModel;
import co.com.gsdd.dw2.persistence.entities.AttackTargetType;
import co.com.gsdd.dw2.repository.AttackTargetTypeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttackTargetTypeService extends AbstractService<AttackTargetType, AttackTargetTypeModel> {

	private final AttackTargetTypeRepository attackTargetTypeRepository;
	private final GenericConverter<AttackTargetType, AttackTargetTypeModel> attackTargetTypeConverter;

	@Override
	public String getSortArg() {
		return "attackTargetTypeId";
	}

	@Override
	public AttackTargetType replaceId(AttackTargetType entityNew, AttackTargetType entityOrig) {
		entityNew.setAttackTargetTypeId(entityOrig.getAttackTargetTypeId());
		return entityNew;
	}

	@Override
	public JpaRepository<AttackTargetType, Long> getRepo() {
		return attackTargetTypeRepository;
	}

	@Override
	public GenericConverter<AttackTargetType, AttackTargetTypeModel> getConverter() {
		return attackTargetTypeConverter;
	}

}
