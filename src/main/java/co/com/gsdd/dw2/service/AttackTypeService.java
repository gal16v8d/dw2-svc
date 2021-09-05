package co.com.gsdd.dw2.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.hateoas.AttackTypeModel;
import co.com.gsdd.dw2.persistence.entities.AttackType;
import co.com.gsdd.dw2.repository.AttackTypeRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttackTypeService extends AbstractService<AttackType, AttackTypeModel> {
	
	private final AttackTypeRepository attackTypeRepository;
	private final GenericConverter<AttackType, AttackTypeModel> attackTypeConverter;

	@Override
	public String getSortArg() {
		return "attackTypeId";
	}

	@Override
	public AttackType replaceId(AttackType entityNew, AttackType entityOrig) {
		entityNew.setAttackTypeId(entityOrig.getAttackTypeId());
		return entityNew;
	}

	@Override
	public JpaRepository<AttackType, Long> getRepo() {
		return attackTypeRepository;
	}

	@Override
	public GenericConverter<AttackType, AttackTypeModel> getConverter() {
		return attackTypeConverter;
	}

}
