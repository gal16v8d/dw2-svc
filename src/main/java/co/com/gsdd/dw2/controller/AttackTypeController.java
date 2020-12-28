package co.com.gsdd.dw2.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.gsdd.dw2.converter.GenericConverter;
import co.com.gsdd.dw2.model.AttackTypeModel;
import co.com.gsdd.dw2.persistence.entities.AttackType;
import co.com.gsdd.dw2.repository.AttackTypeRepository;

@RefreshScope
@RestController
@RequestMapping("v1/attackTypes")
public class AttackTypeController extends AbstractController<AttackType, AttackTypeModel> {

	private final AttackTypeRepository attackTypeRepository;
	private final GenericConverter<AttackType, AttackTypeModel> attackTypeConverter;

	@Autowired
	public AttackTypeController(GenericConverter<AttackType, AttackTypeModel> attackTypeConverter,
			AttackTypeRepository attackTypeRepository) {
		this.attackTypeConverter = attackTypeConverter;
		this.attackTypeRepository = attackTypeRepository;
	}
	
	@Override
	public String getSortArg() {
		return "attackTypeId";
	}
	
	@Override
	public Long getId(AttackType entity) {
		return entity.getAttackTypeId();
	}

	@Override
	public JpaRepository<AttackType, Long> getRepo() {
		return attackTypeRepository;
	}

	@Override
	public GenericConverter<AttackType, AttackTypeModel> getConverter() {
		return attackTypeConverter;
	}

	@PutMapping("{attackTypeId:[0-9]+}")
	public ResponseEntity<AttackTypeModel> update(@PathVariable("attackTypeId") Long attackTypeId,
			@Valid @RequestBody AttackTypeModel model) {
		return getRepo().findById(attackTypeId).map((AttackType dbEntity) -> {
			AttackType at = getConverter().convertToEntity(model);
			return Optional.ofNullable(at).map((AttackType a) -> {
				a.setAttackTypeId(attackTypeId);
				return getRepo().saveAndFlush(a);
			}).orElse(null);
		}).map(this::defineModelWithLinks).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

}
