package co.com.gsdd.dw2.model.hateoas;

import javax.validation.constraints.NotEmpty;

import org.springframework.hateoas.RepresentationModel;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;

@Generated
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class AttackTypeModel extends RepresentationModel<AttackTypeModel> {

	private Long attackTypeId;

	@NotEmpty(message = "attack type name should not be empty")
	private String name;

}
