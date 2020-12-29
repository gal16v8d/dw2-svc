package co.com.gsdd.dw2.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.AttackTypeModel;
import co.com.gsdd.dw2.persistence.entities.AttackType;
import co.com.gsdd.dw2.repository.AttackTypeRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RefreshScope
@RestController
@RequestMapping("v1/attackTypes")
public class AttackTypeController extends AbstractController<AttackType, AttackTypeModel> {

	private final AttackTypeRepository attackTypeRepository;
	private final GenericConverter<AttackType, AttackTypeModel> attackTypeConverter;
	
	@Override
	public String getSortArg() {
		return "attackTypeId";
	}
	
	@Override
	public Long getId(AttackType entity) {
		return entity.getAttackTypeId();
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
